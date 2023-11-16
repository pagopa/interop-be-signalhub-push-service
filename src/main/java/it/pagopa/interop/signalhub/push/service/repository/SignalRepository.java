package it.pagopa.interop.signalhub.push.service.repository;

import it.pagopa.interop.signalhub.push.service.entities.Signal;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface SignalRepository extends ReactiveCrudRepository<Signal, Long> {

    @Query("SELECT * FROM SIGNAL s WHERE s.eservice_id = :eserviceId AND s.signal_id = :signalId")
    Mono<Signal> findBySignalIdAndEServiceId(Long signalId, String eserviceId);

}


