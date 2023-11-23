package it.pagopa.interop.signalhub.push.service.service.impl;

import it.pagopa.interop.signalhub.push.service.entities.EService;
import it.pagopa.interop.signalhub.push.service.exception.PDNDGenericException;
import it.pagopa.interop.signalhub.push.service.mapper.EServiceMapper;
import it.pagopa.interop.signalhub.push.service.repository.EServiceRepository;
import it.pagopa.interop.signalhub.push.service.cache.repository.EServiceCacheRepository;
import it.pagopa.interop.signalhub.push.service.service.OrganizationService;
import it.pagopa.interop.signalhub.push.service.utils.Const;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static it.pagopa.interop.signalhub.push.service.exception.ExceptionTypeEnum.*;


@Slf4j
@Service
@AllArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {

    private final EServiceRepository eServiceRepository;
    private final EServiceCacheRepository cacheRepository;
    private final EServiceMapper mapper;

    @Override
    public Mono<EService> getEService(String eserviceId, String producerId) {

        return this.cacheRepository.findById(eserviceId, producerId)
                .doOnNext(cache -> log.info("[{} - {}] EService in cache", eserviceId, producerId))
                .flatMap(eServiceCache -> {
                    if(eServiceCache.getState().equalsIgnoreCase(Const.STATE_PUBLISHED)) return Mono.just(eServiceCache);
                    return Mono.error(new PDNDGenericException(UNAUTHORIZED, UNAUTHORIZED.getMessage().concat(eserviceId), HttpStatus.FORBIDDEN));
                })
                .switchIfEmpty(Mono.defer(() -> {
                    log.info("[{} - {}] EService no in cache",  eserviceId, producerId);
                    return Mono.empty();
                }))
                .map(mapper::toEntity)
                .switchIfEmpty(getFromDbAndSaveOnCache(eserviceId, producerId));

    }

    private Mono<EService> getFromDbAndSaveOnCache(String eserviceId, String producerId) {

        return this.eServiceRepository.findByProducerIdAndEServiceIdAndState(eserviceId, producerId, Const.STATE_PUBLISHED)
                .switchIfEmpty(Mono.defer(()-> {
                    log.info("[{} - {}] EService not founded into DB",  eserviceId, producerId);
                    return Mono.error(new PDNDGenericException(UNAUTHORIZED, UNAUTHORIZED.getMessage().concat(eserviceId), HttpStatus.FORBIDDEN));
                }))
                .doOnNext(entity ->
                        log.info("[{} - {}] EService founded into DB",  eserviceId, producerId)
                )
                .collectList()
                .map(list -> list.get(0))
                .flatMap(entity -> this.cacheRepository.save(mapper.toCache(entity)))
                .map(mapper::toEntity);
    }

}
