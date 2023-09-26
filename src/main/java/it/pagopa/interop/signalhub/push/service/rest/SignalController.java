package it.pagopa.interop.signalhub.push.service.rest;

import it.pagopa.interop.signalhub.push.service.dto.Signal;
import it.pagopa.interop.signalhub.push.service.dto.SignalRequest;
import it.pagopa.interop.signalhub.push.service.rest.v1.api.GatewayApi;
import it.pagopa.interop.signalhub.push.service.service.SignalService;
import it.pagopa.interop.signalhub.push.service.utils.Const;
import it.pagopa.interop.signalhub.push.service.utils.Utility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
@Slf4j
@RestController
public class SignalController implements GatewayApi {

    @Autowired
    private SignalService signalService;

    @Override
    public Mono<ResponseEntity<Signal>> pushSignal(Mono<SignalRequest> signalRequest, ServerWebExchange exchange) {
       return Utility.getFromContext(Const.ORGANIZATION_ID_VALUE, null)
                .switchIfEmpty(Mono.defer(() -> {
                    log.info("empty");
                    return Mono.empty();
                }))
                .zipWith(signalRequest)
                .flatMap(organizationAndSignalRequest -> {
                    log.info("flat map");
                    return this.signalService.pushSignal(organizationAndSignalRequest.getT1(), organizationAndSignalRequest.getT2());
                }).map(signalResponse -> ResponseEntity.status(HttpStatus.OK).body(signalResponse));

    }
}
