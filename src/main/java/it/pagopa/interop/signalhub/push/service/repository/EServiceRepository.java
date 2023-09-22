package it.pagopa.interop.signalhub.push.service.repository;

import it.pagopa.interop.signalhub.push.service.entities.EService;
import org.springframework.data.r2dbc.repository.Query;
import reactor.core.publisher.Mono;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;


public interface EServiceRepository extends ReactiveCrudRepository<EService, Long> {

    @Query("SELECT * FROM EService WHERE eserviceId = :eserviceId AND organizationId = :organizationId")
    Mono<EService> findByOrganizationIdAndEServiceId(String organizationId, String eserviceId);

    @Query("SELECT * FROM EService WHERE eserviceId = :eserviceId AND signalId = :signalId")
    Mono<EService> findBySignalIdAndEServiceId(String signalId, String eserviceId);
}


