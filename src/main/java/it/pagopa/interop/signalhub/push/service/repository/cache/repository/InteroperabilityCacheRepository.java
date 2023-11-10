package it.pagopa.interop.signalhub.push.service.repository.cache.repository;


import it.pagopa.interop.signalhub.push.service.generated.openapi.client.interop.model.v1.Agreement;
import it.pagopa.interop.signalhub.push.service.repository.cache.model.EServiceCache;
import it.pagopa.interop.signalhub.push.service.repository.cache.model.JWTCache;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;
import java.util.function.Predicate;


@Slf4j
@Repository
@AllArgsConstructor
public class InteroperabilityCacheRepository {
    private final ReactiveRedisOperations<String, Agreement> reactiveRedisOperations;

    public Mono<Agreement> findById(String purposeId) {
        return this.reactiveRedisOperations.opsForValue().get(purposeId)
                .flatMap(finded -> {
                    if (ObjectUtils.isEmpty(finded)) return Mono.empty();
                    return Mono.just(finded);
                });
    }

    public Mono<Agreement> save(Agreement agreement, String purposeId){
        return this.reactiveRedisOperations.opsForValue()
                .set(purposeId, agreement)
                .thenReturn(agreement);
    }


}
