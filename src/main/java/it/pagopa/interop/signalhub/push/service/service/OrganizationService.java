package it.pagopa.interop.signalhub.push.service.service;

import it.pagopa.interop.signalhub.push.service.entities.EService;
import reactor.core.publisher.Mono;


public interface OrganizationService {


    Mono<EService> getEService(String eserviceId, String producerId);

}


