package it.pagopa.interop.signalhub.push.service.service.impl;

import it.pagopa.interop.signalhub.push.service.dto.Signal;
import it.pagopa.interop.signalhub.push.service.dto.SignalRequest;
import it.pagopa.interop.signalhub.push.service.exception.ExceptionTypeEnum;
import it.pagopa.interop.signalhub.push.service.exception.PDNDGenericException;
import it.pagopa.interop.signalhub.push.service.mapper.SignalMapper;
import it.pagopa.interop.signalhub.push.service.queue.producer.InternalSqsProducer;
import it.pagopa.interop.signalhub.push.service.repository.EServiceRepository;
import it.pagopa.interop.signalhub.push.service.repository.SignalRepository;
import it.pagopa.interop.signalhub.push.service.service.SignalService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Slf4j
@Service
@AllArgsConstructor
public class SignalServiceImpl implements SignalService {

    @Autowired
    private EServiceRepository eServiceRepository;

    @Autowired
    private SignalRepository signalRepository;

    @Autowired
    private SignalMapper signalMapper;

    @Autowired
    private InternalSqsProducer internalSqsProducer;

    @Override
    public Mono<Signal> pushSignal(String producerId, SignalRequest signalRequest) {
        return eServiceRepository.findByProducerIdAndEServiceId(producerId, signalRequest.getEserviceId())
                .switchIfEmpty(Mono.error(new PDNDGenericException(ExceptionTypeEnum.CORRESPONDENCE_NOT_FOUND, ExceptionTypeEnum.CORRESPONDENCE_NOT_FOUND.getMessage().concat(signalRequest.getEserviceId()), HttpStatus.FORBIDDEN)))
                .flatMap(eservice -> signalRepository.findBySignalIdAndEServiceId(signalRequest.getIndexSignal(), signalRequest.getEserviceId()))
                .flatMap(eservice -> {
                    log.debug("eservice = {}, SIGNALID_ALREADY_EXISTS", eservice);
                    return Mono.error(new PDNDGenericException(ExceptionTypeEnum.SIGNALID_ALREADY_EXISTS, ExceptionTypeEnum.SIGNALID_ALREADY_EXISTS.getMessage().concat(signalRequest.getEserviceId()), HttpStatus.FORBIDDEN));
                }).switchIfEmpty(Mono.defer(() ->{
                    log.debug("SignalRequest = {}, push signal", signalRequest);
                    return internalSqsProducer.push(signalMapper.toEvent(signalRequest));
                })).thenReturn(signalMapper.toSignal(signalRequest));
    }

}
