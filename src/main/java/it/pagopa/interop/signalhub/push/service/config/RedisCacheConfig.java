package it.pagopa.interop.signalhub.push.service.config;

import it.pagopa.interop.signalhub.push.service.repository.cache.model.EServiceCache;
import it.pagopa.interop.signalhub.push.service.repository.cache.model.JWTCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

@Configuration
@Slf4j
public class RedisCacheConfig {


    @Bean
    public ReactiveRedisTemplate<String, EServiceCache> reactiveRedisTemplate(ReactiveRedisConnectionFactory factory) {
        return new ReactiveRedisTemplate<>(
                factory,
                RedisSerializationContext.fromSerializer(new Jackson2JsonRedisSerializer(EServiceCache.class))
        );
    }

    @Bean
    public ReactiveRedisTemplate<String, JWTCache> reactiveRedisTemplateJWT(ReactiveRedisConnectionFactory factory) {
        return new ReactiveRedisTemplate<>(
                factory,
                RedisSerializationContext.fromSerializer(new Jackson2JsonRedisSerializer(JWTCache.class))
        );
    }
}