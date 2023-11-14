package it.pagopa.interop.signalhub.push.service.service.impl;

import it.pagopa.interop.signalhub.push.service.auth.PrincipalAgreement;
import it.pagopa.interop.signalhub.push.service.cache.repository.InteroperabilityCacheRepository;
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
    void getPrincipalFromPurposeIdAndNotFindInCache() {
        Mockito.when(cacheRepository.findById(Mockito.any())).thenReturn(Mono.empty());
        Mockito.when(cacheRepository.save(Mockito.any(), Mockito.any())).thenReturn(Mono.just(new Agreement()));
        Mockito.when(interoperabilityClient.getAgreementByPurposeId(Mockito.any())).thenReturn(Mono.just(new Agreement()));
        Mockito.when(principalAgreementMapper.toPrincipal(Mockito.any())).thenReturn(new PrincipalAgreement());

        assertNotNull(interopService.getPrincipalFromPurposeId("test").block());
    }

    @Test
    void getPrincipalFromPurposeId() {
    }
}