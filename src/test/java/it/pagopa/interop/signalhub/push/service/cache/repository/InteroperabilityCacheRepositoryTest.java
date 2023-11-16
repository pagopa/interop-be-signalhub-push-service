package it.pagopa.interop.signalhub.push.service.cache.repository;

import it.pagopa.interop.signalhub.push.service.generated.openapi.client.interop.model.v1.Agreement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveValueOperations;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class InteroperabilityCacheRepositoryTest {

    @InjectMocks
    private InteroperabilityCacheRepository cacheRepository;

    @Mock
    private ReactiveRedisOperations<String, Agreement> reactiveRedisOperations;

    @Mock
    private ReactiveValueOperations<String, Agreement> valueOperations;


    @BeforeEach
    void inizialize(){
        Mockito.when(reactiveRedisOperations.opsForValue()).thenReturn(valueOperations);
    }

    @Test
    void findById() {
        Mockito.when(reactiveRedisOperations.opsForValue().get(Mockito.any())).thenReturn(Mono.just(new Agreement()));
        assertNotNull(cacheRepository.findById("123").block());

    }

    @Test
    void findByIdButAgreementNotInCache() {
        Mockito.when(reactiveRedisOperations.opsForValue().get(Mockito.any())).thenReturn(Mono.empty());
        assertNull(cacheRepository.findById("123").block());

    }

    @Test
    void save() {
        Mockito.when(reactiveRedisOperations.opsForValue().set(Mockito.any(),Mockito.any())).thenReturn(Mono.just(true));
        assertNotNull(cacheRepository.save(new Agreement(),"123").block());
    }
}