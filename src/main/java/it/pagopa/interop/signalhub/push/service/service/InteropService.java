package it.pagopa.interop.signalhub.push.service.service;

import it.pagopa.interop.signalhub.push.service.auth.PrincipalAgreement;
import reactor.core.publisher.Mono;

public interface InteropService {


    Mono<PrincipalAgreement> getPrincipalFromPurposeId(String purposeId);

}
