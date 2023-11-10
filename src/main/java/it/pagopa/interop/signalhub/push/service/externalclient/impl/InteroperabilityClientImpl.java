package it.pagopa.interop.signalhub.push.service.externalclient.impl;


import it.pagopa.interop.signalhub.push.service.externalclient.InteroperabilityClient;
import it.pagopa.interop.signalhub.push.service.generated.openapi.client.interop.api.v1.GatewayApi;
import it.pagopa.interop.signalhub.push.service.generated.openapi.client.interop.model.v1.Agreement;
import it.pagopa.interop.signalhub.push.service.repository.cache.repository.InteroperabilityCacheRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;



@Slf4j
@Component
@AllArgsConstructor
public class InteroperabilityClientImpl implements InteroperabilityClient {
    private GatewayApi gatewayApi;
    private final InteroperabilityCacheRepository cacheRepository;


    @Override
    public Mono<Agreement> getAgreementByPurposeId(String purposeId) {

        return this.cacheRepository.findById(purposeId)
                .doOnNext(cache -> log.info("[{}] purposeId in cache", purposeId))
                .switchIfEmpty(Mono.defer(() -> {
                    log.info("[{}] purposeId no in cache",  purposeId);
                    return getFromDbAndSaveOnCache(purposeId);
                }));

    }


    public Mono<Agreement> getFromDbAndSaveOnCache(String purposeId){
        UUID purposeUuid = UUID.fromString(purposeId);
        return gatewayApi.getAgreementByPurpose(purposeUuid)
                .flatMap(entity -> this.cacheRepository.save(entity, purposeId))
                .doOnNext(agreement -> log.info("[[{} - {}] Agreement saved on cache", purposeId, agreement.getId()));
    }

}