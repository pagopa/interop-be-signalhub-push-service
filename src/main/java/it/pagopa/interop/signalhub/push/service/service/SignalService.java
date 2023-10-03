package it.pagopa.interop.signalhub.push.service.service;

import it.pagopa.interop.signalhub.push.service.dto.Signal;
import it.pagopa.interop.signalhub.push.service.dto.SignalRequest;
import reactor.core.publisher.Mono;

public interface SignalService {

    Mono<Signal> pushSignal (String organizationId, SignalRequest signalRequest );


}
