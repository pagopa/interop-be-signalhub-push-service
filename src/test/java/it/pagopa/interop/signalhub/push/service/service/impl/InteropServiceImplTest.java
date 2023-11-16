package it.pagopa.interop.signalhub.push.service.service.impl;

import it.pagopa.interop.signalhub.push.service.auth.PrincipalAgreement;
import it.pagopa.interop.signalhub.push.service.cache.repository.InteroperabilityCacheRepository;
import it.pagopa.interop.signalhub.push.service.exception.ExceptionTypeEnum;
import it.pagopa.interop.signalhub.push.service.exception.PDNDGenericException;
import it.pagopa.interop.signalhub.push.service.externalclient.InteroperabilityClient;
import it.pagopa.interop.signalhub.push.service.generated.openapi.client.interop.model.v1.Agreement;
import it.pagopa.interop.signalhub.push.service.mapper.PrincipalAgreementMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class InteropServiceImplTest {

    @Mock
    private InteroperabilityClient interoperabilityClient;

    @Mock
    private PrincipalAgreementMapper principalAgreementMapper;

    @Mock
    private InteroperabilityCacheRepository cacheRepository;

    @InjectMocks
    private  InteropServiceImpl interopService;

    @BeforeEach
    void setUp(){
    }

    @Test
    void getPrincipalFromPurposeIdAndNotFoundInCache() {
        Mockito.when(cacheRepository.findById(Mockito.any())).thenReturn(Mono.empty());
        Mockito.when(cacheRepository.save(Mockito.any(), Mockito.any())).thenReturn(Mono.just(new Agreement()));
        Mockito.when(interoperabilityClient.getAgreementByPurposeId(Mockito.any())).thenReturn(Mono.just(new Agreement()));
        Mockito.when(principalAgreementMapper.toPrincipal(Mockito.any())).thenReturn(new PrincipalAgreement());

        assertNotNull(interopService.getPrincipalFromPurposeId("test").block());
    }

    @Test
    void getPrincipalFromPurposeIdAndFoundInCache() {
        Mockito.when(cacheRepository.findById(Mockito.any())).thenReturn(Mono.just(new Agreement()));
        Mockito.when(principalAgreementMapper.toPrincipal(Mockito.any())).thenReturn(new PrincipalAgreement());

        assertNotNull(interopService.getPrincipalFromPurposeId("test").block());
    }

    @Test
    void getPrincipalFromPurposeButDetailAgreementError() {
        Mockito.when(cacheRepository.findById(Mockito.any())).thenReturn(Mono.just(new Agreement()));
        Mockito.when(principalAgreementMapper.toPrincipal(Mockito.any())).thenReturn(null);

        StepVerifier.create(interopService.getPrincipalFromPurposeId("test"))
                .expectErrorMatches((ex) -> {
                    assertTrue(ex instanceof PDNDGenericException);
                    assertEquals(ExceptionTypeEnum.DETAIL_AGREEMENT_ERROR, ((PDNDGenericException) ex).getExceptionType());
                    return true;
                }).verify();
    }
}