package it.pagopa.interop.signalhub.push.service.service.impl;

import it.pagopa.interop.signalhub.push.service.dto.Signal;
import it.pagopa.interop.signalhub.push.service.dto.SignalRequest;
import it.pagopa.interop.signalhub.push.service.dto.SignalType;
import it.pagopa.interop.signalhub.push.service.exception.ExceptionTypeEnum;
import it.pagopa.interop.signalhub.push.service.exception.PDNDGenericException;
import it.pagopa.interop.signalhub.push.service.mapper.SignalMapper;
import it.pagopa.interop.signalhub.push.service.queue.model.SignalEvent;
import it.pagopa.interop.signalhub.push.service.queue.producer.InternalSqsProducer;
import it.pagopa.interop.signalhub.push.service.repository.SignalRepository;
import it.pagopa.interop.signalhub.push.service.service.OrganizationService;
import it.pagopa.interop.signalhub.push.service.service.SignalService;
import it.pagopa.interop.signalhub.push.service.utils.Const;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Slf4j
@Service
@AllArgsConstructor
public class SignalServiceImpl implements SignalService {
    private final OrganizationService organizationEService;
    private final SignalRepository signalRepository;
    private final SignalMapper signalMapper;
    private final InternalSqsProducer internalSqsProducer;

    @Override
    public Mono<Signal> pushSignal(String producerId, SignalRequest signalRequest) {
        return organizationEService.getEService(signalRequest.getEserviceId(), producerId)
                .switchIfEmpty(Mono.error(new PDNDGenericException(ExceptionTypeEnum.UNAUTHORIZED, ExceptionTypeEnum.UNAUTHORIZED.getMessage(), HttpStatus.FORBIDDEN)))
                .flatMap(eservice -> signalRepository.findBySignalIdAndEServiceId(signalRequest.getSignalId(), signalRequest.getEserviceId()))
                .flatMap(eservice -> {
                    log.debug("eservice = {}, SIGNALID_ALREADY_EXISTS", eservice);
                    return Mono.error(new PDNDGenericException(ExceptionTypeEnum.SIGNALID_ALREADY_EXISTS, ExceptionTypeEnum.SIGNALID_ALREADY_EXISTS.getMessage()
                            .concat(" ")
                            .concat(signalRequest.getEserviceId()), HttpStatus.BAD_REQUEST));
                }).switchIfEmpty(Mono.defer(() ->{
                    log.debug("SignalRequest = {}, push signal", signalRequest);
                    SignalEvent event = signalMapper.toEvent(signalRequest);
                    if (signalRequest.getSignalType() == SignalType.SEEDUPDATE) {
                        event.setObjectType(Const.SEED_UPDATE);
                    }
                    return internalSqsProducer.push(event);
                })).thenReturn(signalMapper.toSignal(signalRequest));
    }

}
