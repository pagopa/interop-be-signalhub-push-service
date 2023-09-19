package it.pagopa.interop.signalhub.push.service.service.impl;

import it.pagopa.interop.signalhub.push.service.dto.SignalRequest;
import it.pagopa.interop.signalhub.push.service.entities.EService;
import it.pagopa.interop.signalhub.push.service.exception.ExceptionTypeEnum;
import it.pagopa.interop.signalhub.push.service.exception.PnGenericException;
import it.pagopa.interop.signalhub.push.service.mapper.SignalMapper;
import it.pagopa.interop.signalhub.push.service.queue.model.SignalEvent;
import it.pagopa.interop.signalhub.push.service.queue.producer.InternalSqsProducer;
import it.pagopa.interop.signalhub.push.service.repository.EServiceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SignalServiceImplTest {

    @InjectMocks
    private SignalServiceImpl signalService;

    @Mock
    private SignalMapper signalMapper;

    @Mock
    private InternalSqsProducer internalSqsProducer;

    @Mock
    private EServiceRepository eServiceRepository;


    @Test
    void whenCallPushSignalAndCorrespondenceNotFound() {
        SignalRequest signalRequest = getSignalRequest();
        Mockito.when(eServiceRepository.findByOrganizationIdAndEServiceId(Mockito.any(), Mockito.any())).thenReturn(Mono.empty());
        StepVerifier.create(signalService.pushSignal("1234", signalRequest))
                .expectErrorMatches((ex) -> {
                    assertTrue(ex instanceof PnGenericException);
                    assertEquals(ExceptionTypeEnum.CORRESPONDENCE_NOT_FOUND, ((PnGenericException) ex).getExceptionType());
                    return true;
                }).verify();
    }

    @Test
    void whenCallPushSignalAndSignalIdAlreadyExists() {
        SignalRequest signalRequest = getSignalRequest();
        Mockito.when(eServiceRepository.findByOrganizationIdAndEServiceId(Mockito.any(), Mockito.any())).thenReturn(Mono.just(new EService()));
        Mockito.when(eServiceRepository.findBySignalIdAndEServiceId(Mockito.any(), Mockito.any())).thenReturn(Mono.empty());
        StepVerifier.create(signalService.pushSignal("1234", signalRequest))
                .expectErrorMatches((ex) -> {
                    assertTrue(ex instanceof PnGenericException);
                    assertEquals(ExceptionTypeEnum.SIGNALID_ALREADY_EXISTS, ((PnGenericException) ex).getExceptionType());
                    return true;
                }).verify();
    }

    @Test
    void whenCallPushSignalAndSignalIdAlreadyExist() {
        SignalRequest signalRequest = getSignalRequest();
        EService eService= new EService();
        eService.setEserviceId("123");
        eService.setOrganizationId("123");
        Mockito.when(eServiceRepository.findByOrganizationIdAndEServiceId(Mockito.any(), Mockito.any())).thenReturn(Mono.just(eService));

       assertNotNull(signalService.pushSignal("test", signalRequest));
    }

    private SignalRequest getSignalRequest(){
        SignalRequest request = new SignalRequest();
        request.setEserviceId("123");
        request.setIndexSignal(1L);
        request.setObjectId("test");
        request.setObjectType("test");
        return request;
    }

}