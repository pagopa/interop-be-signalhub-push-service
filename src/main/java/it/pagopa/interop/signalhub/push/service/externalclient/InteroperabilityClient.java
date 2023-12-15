package it.pagopa.interop.signalhub.push.service.externalclient;


import it.pagopa.interop.signalhub.push.service.generated.openapi.client.interop.model.v1.Agreement;
import reactor.core.publisher.Mono;

public interface InteroperabilityClient {

    Mono<Agreement> getAgreementByPurposeId(String purposeId);

}
