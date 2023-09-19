package it.pagopa.interop.signalhub.push.service.rest;

import it.pagopa.interop.signalhub.push.service.LocalStackTestConfig;
import it.pagopa.interop.signalhub.push.service.dto.Signal;
import it.pagopa.interop.signalhub.push.service.dto.SignalRequest;
import it.pagopa.interop.signalhub.push.service.service.SignalService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@Import(LocalStackTestConfig.class)

@ActiveProfiles("test")
@WebFluxTest(controllers = {SignalController.class})
class SignalControllerTest {

    @Autowired
    private WebTestClient webTestClient;
    @MockBean
    private SignalService signalService;

    @Test
    void pushSignal() {
        Signal signalResponse= new Signal();
        String path = "/push-signal";
        Mockito.when(signalService.pushSignal(Mockito.any(), Mockito.any()))
                .thenReturn(Mono.just(signalResponse));

        webTestClient.post()
                .uri(uriBuilder -> uriBuilder.path(path)
                        .build())
                .bodyValue(getSignalRequest())
                .exchange()
                .expectStatus().isOk();
    }

    private SignalRequest getSignalRequest(){
        SignalRequest request = new SignalRequest();
        request.setEserviceId("123");
        request.setIndexSignal(Long.valueOf(1));
        request.setObjectId("test");
        request.setObjectType("test");
        return request;
    }
}