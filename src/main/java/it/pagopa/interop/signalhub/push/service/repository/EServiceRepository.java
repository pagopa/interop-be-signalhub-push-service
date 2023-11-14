package it.pagopa.interop.signalhub.push.service.repository;

import it.pagopa.interop.signalhub.push.service.entities.EService;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Repository
public interface EServiceRepository extends ReactiveCrudRepository<EService, String> {


    @Query("SELECT * FROM ESERVICE e WHERE e.eservice_id = :eserviceId AND e.producer_id = :producerId AND UPPER(e.state) = UPPER(:state)")
    Flux<EService> findByProducerIdAndEServiceIdAndState(String eserviceId, String producerId, String state);

}


