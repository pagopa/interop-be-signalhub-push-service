package it.pagopa.interop.signalhub.push.service.repository;

import it.pagopa.interop.signalhub.push.service.entities.EService;
import org.springframework.data.r2dbc.repository.Query;
import reactor.core.publisher.Mono;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;


public interface EServiceRepository extends ReactiveCrudRepository<EService, Long> {

    @Query("SELECT * FROM ORGANIZATION_ESERVICE s WHERE s.eservice_id = :eserviceId AND s.organization_id = :organizationId")
    Mono<EService> findByOrganizationIdAndEServiceId(String organizationId, String eserviceId);

}


