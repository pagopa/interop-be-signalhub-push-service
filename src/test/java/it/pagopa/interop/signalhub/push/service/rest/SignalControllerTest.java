package it.pagopa.interop.signalhub.push.service.rest;

import it.pagopa.interop.signalhub.push.service.config.BaseTest;
import it.pagopa.interop.signalhub.push.service.config.WithMockCustomUser;
import it.pagopa.interop.signalhub.push.service.dto.Signal;
import it.pagopa.interop.signalhub.push.service.dto.SignalRequest;
import it.pagopa.interop.signalhub.push.service.dto.SignalType;
import it.pagopa.interop.signalhub.push.service.service.SignalService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;



class SignalControllerTest extends BaseTest.WithWebEnvironment {
    @Autowired
    private WebTestClient webTestClient;
    @MockBean
    private SignalService signalService;

    @Test
    @WithMockCustomUser
    void pushSignal() {
        Signal signalResponse= new Signal();
        String path = "/push-signal";
        Mockito.when(signalService.pushSignal(Mockito.any(), Mockito.any()))
                .thenReturn(Mono.just(signalResponse));

        webTestClient
                .mutateWith(SecurityMockServerConfigurers.csrf())
                .post()
                .uri(uriBuilder -> uriBuilder.path(path)
                        .build())
                .header(HttpHeaders.AUTHORIZATION, TOKEN_OK)
                .bodyValue(getSignalRequest())
                .exchange()
                .expectStatus().isOk();
    }

    private SignalRequest getSignalRequest(){
        SignalRequest request = new SignalRequest();
        request.setSignalType(SignalType.CREATE);
        request.setEserviceId("123");
        request.setIndexSignal(Long.valueOf(2));
        request.setObjectId("test");
        request.setObjectType("test");
        return request;
    }
}