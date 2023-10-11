package it.pagopa.interop.signalhub.push.service.repository.impl;

import it.pagopa.interop.signalhub.push.service.mapper.EServiceMapper;
import it.pagopa.interop.signalhub.push.service.repository.cache.repository.EServiceCacheRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EServiceRepositoryImplTest {

    @InjectMocks
    private EServiceRepositoryImpl eServiceRepositoryimpl;

    @Mock
    private EServiceCacheRepository cacheRepository;

    @Mock
    private EServiceMapper mapper;

    @Mock
    private R2dbcEntityTemplate template;

    //@Test
    void findByProducerIdAndEServiceId() {
        Mockito.when(cacheRepository.findById(Mockito.any(), Mockito.any())).thenReturn(Mono.empty());
        Mockito.when(template.selectOne(Mockito.any(), Mockito.any())).thenReturn(Mono.empty());
        Mockito.when(cacheRepository.findById(Mockito.any(), Mockito.any())).thenReturn(Mono.empty());
        assertNull(eServiceRepositoryimpl.findByProducerIdAndEServiceId("123", "123").block());
    }
}