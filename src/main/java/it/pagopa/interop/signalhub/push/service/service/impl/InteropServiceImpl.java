package it.pagopa.interop.signalhub.push.service.service.impl;

import it.pagopa.interop.signalhub.push.service.auth.PrincipalAgreement;
import it.pagopa.interop.signalhub.push.service.exception.PDNDGenericException;
import it.pagopa.interop.signalhub.push.service.externalclient.InteroperabilityClient;
import it.pagopa.interop.signalhub.push.service.mapper.PrincipalAgreementMapper;
import it.pagopa.interop.signalhub.push.service.cache.repository.InteroperabilityCacheRepository;
import it.pagopa.interop.signalhub.push.service.service.InteropService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static it.pagopa.interop.signalhub.push.service.exception.ExceptionTypeEnum.DETAIL_AGREEMENT_ERROR;

@Slf4j
@Service
@AllArgsConstructor
public class InteropServiceImpl implements InteropService {
    private final InteroperabilityClient interoperabilityClient;
    private final PrincipalAgreementMapper principalAgreementMapper;
    private final InteroperabilityCacheRepository cacheRepository;

    @Override
    public Mono<PrincipalAgreement> getPrincipalFromPurposeId(String purposeId) {
        return cacheRepository.findById(purposeId)
                .doOnNext(cache -> log.info("[{}] purposeId in cache", purposeId))
                .switchIfEmpty(Mono.defer(() -> {
                    log.info("[{}] purposeId no in cache",  purposeId);
                    return interoperabilityClient.getAgreementByPurposeId(purposeId)
                            .flatMap(agreement -> cacheRepository.save(agreement, purposeId))
                            .doOnNext(agreement -> log.info("[[{} - {}] Agreement saved on cache", purposeId, agreement.getId()));
                }))
                .map(principalAgreementMapper::toPrincipal)
                .onErrorResume(ex ->
                        Mono.error(new PDNDGenericException(DETAIL_AGREEMENT_ERROR, DETAIL_AGREEMENT_ERROR.getMessage())));
    }


}
