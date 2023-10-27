//package it.pagopa.interop.signalhub.push.service.repository.cache.repository;
//
//import it.pagopa.interop.signalhub.push.service.repository.cache.model.EServiceCache;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.data.redis.core.ReactiveRedisOperations;
//import org.springframework.data.redis.core.ReactiveValueOperations;
//import reactor.core.publisher.Mono;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@ExtendWith(MockitoExtension.class)
//class EServiceCacheRepositoryTest {
//    @InjectMocks
//    private EServiceCacheRepository eServiceCacheRepository;
//
//    @Mock
//    private ReactiveRedisOperations<String, EServiceCache> reactiveRedisOperations;
//
//    @Mock
//    private ReactiveValueOperations<String, EServiceCache> valueOperations;
//
//    private EServiceCache eServiceCache;
//
//    @BeforeEach
//    void inizialize(){
//        eServiceCache= new EServiceCache();
//        eServiceCache.setEserviceId("123");
//        eServiceCache.setProducerId("123");
//        Mockito.when(reactiveRedisOperations.opsForValue()).thenReturn(valueOperations);
//    }
//
//    @Test
//    void findById() {
//        Mockito.when(reactiveRedisOperations.opsForValue().get(Mockito.anyString())).thenReturn(Mono.just(eServiceCache));
//        assertNotNull(eServiceCacheRepository.findById("123", "123").block());
//    }
//
//    @Test
//    void findByIdButReturnNull() {
//        Mockito.when(reactiveRedisOperations.opsForValue().get(Mockito.anyString())).thenReturn(Mono.empty());
//        assertNull(eServiceCacheRepository.findById("123", "123").block());
//    }
//
//    @Test
//    void save() {
//        Mockito.when(reactiveRedisOperations.opsForValue().set(Mockito.anyString(), Mockito.any())).thenReturn(Mono.just(true));
//        assertNotNull(eServiceCacheRepository.save(eServiceCache).block());
//    }
//}