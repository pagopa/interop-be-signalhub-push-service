package it.pagopa.interop.signalhub.push.service.repository.impl;

import it.pagopa.interop.signalhub.push.service.entities.EService;
import it.pagopa.interop.signalhub.push.service.exception.PDNDGenericException;
import it.pagopa.interop.signalhub.push.service.mapper.EServiceMapper;
import it.pagopa.interop.signalhub.push.service.repository.EServiceRepository;
import it.pagopa.interop.signalhub.push.service.repository.cache.repository.EServiceCacheRepository;
import it.pagopa.interop.signalhub.push.service.utils.Const;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import static it.pagopa.interop.signalhub.push.service.exception.ExceptionTypeEnum.ESERVICE_STATUS_IS_NOT_ACTIVE;
import static org.springframework.data.relational.core.query.Criteria.where;


@Slf4j
@Repository
@AllArgsConstructor
public class EServiceRepositoryImpl implements EServiceRepository {

    private final R2dbcEntityTemplate template;
    private final EServiceCacheRepository cacheRepository;
    private final EServiceMapper mapper;

    @Override
    public Mono<EService> findByProducerIdAndEServiceId(String eserviceId, String producerId, String descriptorId) {

        return this.cacheRepository.findById(eserviceId, producerId, descriptorId)
                .doOnNext(cache -> log.info("[{} - {}] EService in cache", eserviceId, producerId))
                .flatMap(eServiceCache -> {
                    if(eServiceCache.getState().equals(Const.STATE_ACTIVE)) return Mono.just(eServiceCache);
                    return Mono.error(new PDNDGenericException(ESERVICE_STATUS_IS_NOT_ACTIVE, ESERVICE_STATUS_IS_NOT_ACTIVE.getMessage().concat(eserviceId)));
                })
                .switchIfEmpty(Mono.defer(() -> {
                    log.info("[{} - {}] EService no in cache",  eserviceId, producerId);
                    return Mono.empty();
                }))
                .map(mapper::toEntity)
                .switchIfEmpty(getFromDbAndSaveOnCache(eserviceId, producerId, descriptorId));

    }

    private Mono<EService> getFromDbAndSaveOnCache(String eserviceId, String producerId, String descriptorId) {
        Query equals = Query.query(
                where(EService.COLUMN_ESERVICE_ID).is(eserviceId)
                        .and(where(EService.COLUMN_PRODUCER_ID).is(producerId))
                        .and(where(EService.COLUMN_DESCRIPTOR_ID).is(descriptorId))
                        .and(where(EService.COLUMN_STATE).not(Const.STATE_ACTIVE))
        );
        return this.template.selectOne(equals, EService.class)
                .switchIfEmpty(Mono.defer(()-> {
                    log.info("[{} - {}] EService not founded into DB",  eserviceId, producerId);
                    return Mono.empty();
                }))
                .doOnNext(entity ->
                        log.info("[{} - {}] EService founded into DB",  eserviceId, producerId)
                )
                .flatMap(entity -> this.cacheRepository.save(mapper.toCache(entity)))
                .map(mapper::toEntity);
    }

}
