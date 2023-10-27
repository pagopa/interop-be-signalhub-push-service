//package it.pagopa.interop.signalhub.push.service.repository.impl;
//
//import it.pagopa.interop.signalhub.push.service.entities.EService;
//import it.pagopa.interop.signalhub.push.service.mapper.EServiceMapper;
//import it.pagopa.interop.signalhub.push.service.repository.cache.model.EServiceCache;
//import it.pagopa.interop.signalhub.push.service.repository.cache.repository.EServiceCacheRepository;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
//import reactor.core.publisher.Mono;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@ExtendWith(MockitoExtension.class)
//class EServiceRepositoryImplTest {
//
//    @InjectMocks
//    private EServiceRepositoryImpl eServiceRepositoryimpl;
//
//    @Mock
//    private EServiceCacheRepository cacheRepository;
//
//    @Mock
//    private EServiceMapper mapper;
//
//    @Mock
//    private R2dbcEntityTemplate template;
//
//    @Test
//    void findByProducerIdAndEServiceIdButNotExists() {
//        Mockito.when(cacheRepository.findById(Mockito.any(), Mockito.any())).thenReturn(Mono.empty());
//        Mockito.when(template.selectOne(Mockito.any(), Mockito.any())).thenReturn(Mono.empty());
//        assertNull(eServiceRepositoryimpl.findByProducerIdAndEServiceId("123", "123").block());
//    }
//
//    @Test
//    void findByPbroducerIdAndEServiceIdAndFoundedInCache() {
//        EService eService= new EService();
//        eService.setEserviceId("123");
//        EServiceCache eServiceCache= new EServiceCache();
//        eServiceCache.setEserviceId("123");
//        Mockito.when(cacheRepository.findById(Mockito.any(), Mockito.any())).thenReturn(Mono.just(eServiceCache));
//        Mockito.when(mapper.toEntity(Mockito.any())).thenReturn(eService);
//        Mockito.when(template.selectOne(Mockito.any(), Mockito.any())).thenReturn(Mono.empty());
//        assertNotNull(eServiceRepositoryimpl.findByProducerIdAndEServiceId("123", "123").block());
//    }
//
//    @Test
//    void findByPbroducerIdAndEServiceIdButNotFoundInCache() {
//        Mockito.when(cacheRepository.findById(Mockito.any(), Mockito.any())).thenReturn(Mono.empty());
//        Mockito.when(template.selectOne(Mockito.any(), Mockito.any())).thenReturn(Mono.just(new EService()));
//        Mockito.when(cacheRepository.save(Mockito.any())).thenReturn(Mono.just(new EServiceCache()));
//        Mockito.when(mapper.toEntity(Mockito.any())).thenReturn(new EService());
//        assertNotNull(eServiceRepositoryimpl.findByProducerIdAndEServiceId("123", "123").block());
//    }
//}