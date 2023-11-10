package it.pagopa.interop.signalhub.push.service.config;

import it.pagopa.interop.signalhub.push.service.generated.openapi.client.interop.model.v1.Agreement;
import it.pagopa.interop.signalhub.push.service.repository.cache.model.EServiceCache;
import it.pagopa.interop.signalhub.push.service.repository.cache.model.JWTCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@Slf4j
public class RedisCacheConfig {


    @Bean
    public ReactiveRedisTemplate<String, EServiceCache> reactiveRedisTemplateEservice(ReactiveRedisConnectionFactory factory) {
        StringRedisSerializer keySerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer<EServiceCache> valueSerializer =
                new Jackson2JsonRedisSerializer<>(EServiceCache.class);
        RedisSerializationContext.RedisSerializationContextBuilder<String, EServiceCache> builder =
                RedisSerializationContext.newSerializationContext(keySerializer);
        RedisSerializationContext<String, EServiceCache> context =
                builder.value(valueSerializer).build();
       return new ReactiveRedisTemplate<>(factory, context);
    }



    @Bean
    public ReactiveRedisTemplate<String, Agreement> reactiveRedisTemplateAgreement(ReactiveRedisConnectionFactory factory) {
        StringRedisSerializer keySerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer<Agreement> valueSerializer =
                new Jackson2JsonRedisSerializer<>(Agreement.class);
        RedisSerializationContext.RedisSerializationContextBuilder<String, Agreement> builder =
                RedisSerializationContext.newSerializationContext(keySerializer);
        RedisSerializationContext<String, Agreement> context =
                builder.value(valueSerializer).build();
        return new ReactiveRedisTemplate<>(factory, context);
    }

    @Bean
    public ReactiveRedisTemplate<String, JWTCache> reactiveRedisTemplateJWT(ReactiveRedisConnectionFactory factory) {
        StringRedisSerializer keySerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer<JWTCache> valueSerializer =
                new Jackson2JsonRedisSerializer<>(JWTCache.class);
        RedisSerializationContext.RedisSerializationContextBuilder<String, JWTCache> builder =
                RedisSerializationContext.newSerializationContext(keySerializer);
        RedisSerializationContext<String, JWTCache> context =
                builder.value(valueSerializer).build();
        return new ReactiveRedisTemplate<>(factory, context);
    }
}