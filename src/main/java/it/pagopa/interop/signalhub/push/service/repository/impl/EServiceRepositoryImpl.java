package it.pagopa.interop.signalhub.push.service.repository.impl;

import it.pagopa.interop.signalhub.push.service.entities.EService;
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

import static org.springframework.data.relational.core.query.Criteria.where;


@Slf4j
@Repository
@AllArgsConstructor
public class EServiceRepositoryImpl implements EServiceRepository {

    private final R2dbcEntityTemplate template;
    private final EServiceCacheRepository cacheRepository;
    private final EServiceMapper mapper;

    @Override
    public Mono<EService> findByOrganizationIdAndEServiceId(String organizationId, String eserviceId) {

        return this.cacheRepository.findById(organizationId, eserviceId)
                .doOnNext(cache -> log.info("[{}-{}] EService in cache", organizationId, eserviceId))
                .switchIfEmpty(Mono.defer(() -> {
                    log.info("[{}-{}] EService no in cache", organizationId, eserviceId);
                    return Mono.empty();
                }))
                .map(mapper::toEntity)
                .switchIfEmpty(getFromDbAndSaveOnCache(organizationId, eserviceId));

    }

    private Mono<EService> getFromDbAndSaveOnCache(String organizationId, String eserviceId){
        Query equals = Query.query(
                where(EService.COLUMN_ORGANIZATION_ID).is(organizationId)
                        .and(where(EService.COLUMN_ESERVICE_ID).is(eserviceId))
                        .and(where(EService.COLUMN_STATE).not(Const.STATE_ARCHIVIED))
        );
        return this.template.selectOne(equals, EService.class)
                .switchIfEmpty(Mono.defer(()-> {
                    log.info("[{}-{}] EService not founded into DB", organizationId, eserviceId);
                    return Mono.empty();
                }))
                .doOnNext(entity ->
                        log.info("[{}-{}] EService founded into DB", organizationId, eserviceId)
                )
                .flatMap(entity -> this.cacheRepository.save(mapper.toCache(entity)))
                .doOnNext(cacheEntity ->
                        log.info("[{}-{}] EService saved on cache", organizationId, eserviceId)
                )
                .map(mapper::toEntity);
    }
}
