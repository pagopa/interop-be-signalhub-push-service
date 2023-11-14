package it.pagopa.interop.signalhub.push.service.service.impl;

import it.pagopa.interop.signalhub.push.service.dto.Signal;
import it.pagopa.interop.signalhub.push.service.dto.SignalRequest;
import it.pagopa.interop.signalhub.push.service.dto.SignalType;
import it.pagopa.interop.signalhub.push.service.entities.EService;
import it.pagopa.interop.signalhub.push.service.exception.ExceptionTypeEnum;
import it.pagopa.interop.signalhub.push.service.exception.PDNDGenericException;
import it.pagopa.interop.signalhub.push.service.mapper.SignalMapper;
import it.pagopa.interop.signalhub.push.service.queue.model.SignalEvent;
import it.pagopa.interop.signalhub.push.service.queue.producer.InternalSqsProducer;
import it.pagopa.interop.signalhub.push.service.repository.SignalRepository;
import it.pagopa.interop.signalhub.push.service.service.OrganizationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SignalServiceImplTest {

    @InjectMocks
    private SignalServiceImpl signalService;

    @Mock
    private SignalMapper signalMapper;

    @Mock
    private OrganizationService organizationService;

    @Mock
    private InternalSqsProducer internalSqsProducer;


    @Mock
    private SignalRepository signalRepository;

    @BeforeEach
    void inizialize(){
        Mockito.when(signalMapper.toSignal(Mockito.any())).thenReturn(new Signal());
    }


    @Test
    void whenCallPushSignalAndCorrespondenceNotFound() {
        SignalRequest signalRequest = getSignalRequest();
        Mockito.when(organizationService.getEService(Mockito.any(), Mockito.any())).thenReturn(Mono.empty());
        StepVerifier.create(signalService.pushSignal("1234", signalRequest))
                .expectErrorMatches((ex) -> {
                    assertTrue(ex instanceof PDNDGenericException);
                    assertEquals(ExceptionTypeEnum.CORRESPONDENCE_NOT_FOUND, ((PDNDGenericException) ex).getExceptionType());
                    return true;
                }).verify();
    }

    @Test
    void whenCallPushSignalAndSignalIdAlreadyExists() {
        SignalRequest signalRequest = getSignalRequest();
        Mockito.when(organizationService.getEService(Mockito.any(), Mockito.any())).thenReturn(Mono.just(new EService()));
        Mockito.when(signalRepository.findBySignalIdAndEServiceId(Mockito.any(), Mockito.any())).thenReturn(Mono.just(new it.pagopa.interop.signalhub.push.service.entities.Signal()));

        StepVerifier.create(signalService.pushSignal("1234", signalRequest))
                .expectErrorMatches((ex) -> {
                    assertTrue(ex instanceof PDNDGenericException);
                    assertEquals(ExceptionTypeEnum.SIGNALID_ALREADY_EXISTS, ((PDNDGenericException) ex).getExceptionType());
                    return true;
                }).verify();
    }

    @Test
    void whenCallPushSignalAndReturnSignal() {
        SignalRequest signalRequest = getSignalRequest();
        EService eService= new EService();
        eService.setEserviceId("123");
        eService.setProducerId("123");
        Mockito.when(organizationService.getEService(Mockito.any(), Mockito.any())).thenReturn(Mono.just(eService));
        Mockito.when(signalRepository.findBySignalIdAndEServiceId(Mockito.any(), Mockito.any())).thenReturn(Mono.empty());
        Mockito.when(internalSqsProducer.push(Mockito.any())).thenReturn(Mono.just(new SignalEvent()));

        assertNotNull(signalService.pushSignal("test", signalRequest).block());
    }

    private SignalRequest getSignalRequest(){
        SignalRequest request = new SignalRequest();
        request.setSignalType(SignalType.CREATE);
        request.setEserviceId("123");
        request.setIndexSignal(1L);
        request.setObjectId("test");
        request.setObjectType("test");
        return request;
    }

}