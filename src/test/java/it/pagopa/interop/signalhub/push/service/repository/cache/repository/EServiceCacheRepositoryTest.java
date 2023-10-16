package it.pagopa.interop.signalhub.push.service.repository.cache.repository;

import it.pagopa.interop.signalhub.push.service.repository.cache.model.EServiceCache;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import reactor.core.publisher.Flux;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class EServiceCacheRepositoryTest {
    @InjectMocks
    private EServiceCacheRepository eServiceCacheRepository;

    @Mock
    private ReactiveRedisOperations<String, EServiceCache> reactiveRedisOperations;

    //@Test
    void findAll() {
        Mockito.when(reactiveRedisOperations.opsForList().range(Mockito.any(), 0, -1)).thenReturn(Flux.just(new EServiceCache()));
        assertNotNull(eServiceCacheRepository.findById("123", "123"));
    }
}