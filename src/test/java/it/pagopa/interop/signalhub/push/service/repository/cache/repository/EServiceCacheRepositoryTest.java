package it.pagopa.interop.signalhub.push.service.repository.cache.repository;

import it.pagopa.interop.signalhub.push.service.repository.cache.model.EServiceCache;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.ReactiveListOperations;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EServiceCacheRepositoryTest {
    @InjectMocks
    private EServiceCacheRepository eServiceCacheRepository;

    @Mock
    private ReactiveRedisOperations<String, EServiceCache> reactiveRedisOperations;

    @Mock
    private ReactiveListOperations<String, EServiceCache> listOperations;

    private EServiceCache eServiceCache;

    @BeforeEach
    void inizialize(){
        eServiceCache= new EServiceCache();
        eServiceCache.setEserviceId("123");
        eServiceCache.setProducerId("123");
        Mockito.when(reactiveRedisOperations.opsForList()).thenReturn(listOperations);
    }

    @Test
    void findById() {
        Mockito.when(reactiveRedisOperations.opsForList().range(Mockito.anyString(), Mockito.anyLong(), Mockito.anyLong())).thenReturn(Flux.just(eServiceCache));
        assertNotNull(eServiceCacheRepository.findById("123", "123").block());
    }

    @Test
    void findByIdButReturnNull() {
        Mockito.when(reactiveRedisOperations.opsForList().range(Mockito.anyString(), Mockito.anyLong(), Mockito.anyLong())).thenReturn(Flux.empty());
        assertNull(eServiceCacheRepository.findById("123", "123").block());
    }

    @Test
    void save() {
        Mockito.when(reactiveRedisOperations.opsForList().rightPush(Mockito.anyString(), Mockito.any())).thenReturn(Mono.just(1l));
        assertNotNull(eServiceCacheRepository.save(eServiceCache).block());
    }
}