package it.pagopa.interop.signalhub.push.service.repository;

import it.pagopa.interop.signalhub.push.service.entities.EService;
import reactor.core.publisher.Mono;



public interface EServiceRepository {


    Mono<EService> findByOrganizationIdAndEServiceId(String organizationId, String eserviceId);

}


