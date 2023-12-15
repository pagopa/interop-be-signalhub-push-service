package it.pagopa.interop.signalhub.push.service.service;

import it.pagopa.interop.signalhub.push.service.cache.model.JWTCache;
import reactor.core.publisher.Mono;


public interface JWTService {

    Mono<String> findByJWT(String jwt);
    Mono<JWTCache> saveOnCache(JWTCache jwt);

}


