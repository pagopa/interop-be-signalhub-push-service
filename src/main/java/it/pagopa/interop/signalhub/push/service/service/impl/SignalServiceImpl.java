package it.pagopa.interop.signalhub.push.service.service.impl;

import it.pagopa.interop.signalhub.push.service.dto.Signal;
import it.pagopa.interop.signalhub.push.service.dto.SignalRequest;
import it.pagopa.interop.signalhub.push.service.exception.ExceptionTypeEnum;
import it.pagopa.interop.signalhub.push.service.exception.PnGenericException;
import it.pagopa.interop.signalhub.push.service.mapper.SignalMapper;
import it.pagopa.interop.signalhub.push.service.queue.producer.InternalSqsProducer;
import it.pagopa.interop.signalhub.push.service.repository.EServiceRepository;
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
    private SignalMapper signalMapper;

    @Autowired
    private InternalSqsProducer internalSqsProducer;

    @Override
    public Mono<Signal> pushSignal(String organizationId, SignalRequest signalRequest) {
        return eServiceRepository.findByOrganizationIdAndEServiceId(organizationId, signalRequest.getEserviceId().toString())
                .switchIfEmpty(Mono.error(new PnGenericException(ExceptionTypeEnum.CORRESPONDENCE_NOT_FOUND, ExceptionTypeEnum.CORRESPONDENCE_NOT_FOUND.getMessage().concat(signalRequest.getEserviceId().toString()), HttpStatus.FORBIDDEN)))
                .flatMap(eservice -> eServiceRepository.findBySignalIdAndEServiceId(signalRequest.getIndexSignal().toString(), signalRequest.getEserviceId()))
                .switchIfEmpty(Mono.error(new PnGenericException(ExceptionTypeEnum.SIGNALID_ALREADY_EXISTS, ExceptionTypeEnum.SIGNALID_ALREADY_EXISTS.getMessage(), HttpStatus.BAD_REQUEST)))
                .flatMap(eservice -> internalSqsProducer.push(signalMapper.toEvent(signalRequest)))
                .map(signalEvent -> signalMapper.toSignal(signalEvent));
    }
}
