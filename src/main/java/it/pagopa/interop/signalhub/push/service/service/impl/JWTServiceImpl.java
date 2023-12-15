package it.pagopa.interop.signalhub.push.service.service.impl;

import it.pagopa.interop.signalhub.push.service.exception.PDNDGenericException;
import it.pagopa.interop.signalhub.push.service.cache.model.JWTCache;
import it.pagopa.interop.signalhub.push.service.cache.repository.JWTCacheRepository;
import it.pagopa.interop.signalhub.push.service.service.JWTService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static it.pagopa.interop.signalhub.push.service.exception.ExceptionTypeEnum.JWT_NOT_VALID;


@Slf4j
@Service
@AllArgsConstructor
public class JWTServiceImpl implements JWTService {

    private final JWTCacheRepository cacheRepository;

    @Override
    public Mono<String> findByJWT(String jwt) {

        return this.cacheRepository.findById(jwt)
                .doOnNext(cache -> log.info("JWT in cache"))
                .flatMap(jwtCache -> Mono.error(new PDNDGenericException(JWT_NOT_VALID, JWT_NOT_VALID.getMessage(), HttpStatus.FORBIDDEN)))
                .switchIfEmpty(Mono.defer(() -> {
                    log.info("JWT no in cache");
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
