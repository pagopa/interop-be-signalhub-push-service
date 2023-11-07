package it.pagopa.interop.signalhub.push.service.repository.impl;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.datatype.jdk8.Jdk8UnwrappingOptionalBeanPropertyWriter;
import it.pagopa.interop.signalhub.push.service.entities.EService;
import it.pagopa.interop.signalhub.push.service.entities.Signal;
import it.pagopa.interop.signalhub.push.service.exception.PDNDGenericException;
import it.pagopa.interop.signalhub.push.service.mapper.EServiceMapper;
import it.pagopa.interop.signalhub.push.service.repository.EServiceRepository;
import it.pagopa.interop.signalhub.push.service.repository.JWTRepository;
import it.pagopa.interop.signalhub.push.service.repository.cache.model.JWTCache;
import it.pagopa.interop.signalhub.push.service.repository.cache.repository.EServiceCacheRepository;
import it.pagopa.interop.signalhub.push.service.repository.cache.repository.JWTCacheRepository;
import it.pagopa.interop.signalhub.push.service.utils.Const;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import static it.pagopa.interop.signalhub.push.service.exception.ExceptionTypeEnum.JWT_NOT_VALID;
import static org.springframework.data.relational.core.query.Criteria.where;


@Slf4j
@Repository
@AllArgsConstructor
public class JWTRepositoryImpl implements JWTRepository {

    private final JWTCacheRepository cacheRepository;

    @Override
    public Mono<DecodedJWT> findByJWT(DecodedJWT jwt) {

        return this.cacheRepository.findById(jwt.getToken())
                .doOnNext(cache -> log.debug("JWT in cache"))
                .flatMap(jwtCache -> Mono.error(new PDNDGenericException(JWT_NOT_VALID, JWT_NOT_VALID.getMessage(), HttpStatus.UNAUTHORIZED)))
                .switchIfEmpty(Mono.defer(() -> {
                    log.debug("JWT no in cache");
                    return Mono.just(jwt);
                })).thenReturn(jwt);

    }

    public Mono<JWTCache> saveOnCache(JWTCache jwtCache){
        return this.cacheRepository.save(jwtCache)
                .doOnNext(cacheEntity ->
                        log.info("JWT saved on cache")
                );
    }
}
