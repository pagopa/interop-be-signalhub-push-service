package it.pagopa.interop.signalhub.push.service.repository.cache.repository;


import it.pagopa.interop.signalhub.push.service.repository.cache.model.EServiceCache;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Predicate;

@Slf4j
@Repository
@AllArgsConstructor
public class EServiceCacheRepository {
    private final ReactiveRedisOperations<String, EServiceCache> reactiveRedisOperations;


    public Mono<EServiceCache> findById(String prducerId, String eservice) {
        return this.reactiveRedisOperations.opsForList().range(eservice, 0, -1)
                .filter(correctEservice(prducerId, eservice))
                .collectList()
                .flatMap(finded -> {
                    if (finded.isEmpty()) return Mono.empty();
                    return Mono.just(finded.get(finded.size()-1));
                });
    }


    public Mono<EServiceCache> save(EServiceCache eservice){
        return this.reactiveRedisOperations.opsForList().rightPush(eservice.getEserviceId(), eservice).thenReturn(eservice);
    }

    private Predicate<EServiceCache> correctEservice(String producerId, String eserviceId){
        return eservice -> eservice.getEserviceId().equals(eserviceId) &&
                eservice.getProducerId().equals(producerId);
    }


}
