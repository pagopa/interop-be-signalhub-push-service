package it.pagopa.interop.signalhub.push.service.config;

import it.pagopa.interop.signalhub.push.service.entities.Signal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@Slf4j
public class RedisCacheConfig {

//    @Bean
//    @Primary
//    public ReactiveRedisConnectionFactory reactiveRedisConnectionFactory(RedisConfiguration defaultRedisConfig) {
//        LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder().build();
//        return new LettuceConnectionFactory(defaultRedisConfig, clientConfig);
//    }
//
//    @Bean
//    public RedisConfiguration defaultRedisConfig() {
//        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
//        config.setDatabase(0);
//        config.setUsername("signal-hub-user");
//        config.setPassword(RedisPassword.of("signal-hub"));
//        return config;
//    }

    @Bean
    ReactiveRedisOperations<String, Signal> redisOperations(ReactiveRedisConnectionFactory factory) {
        Jackson2JsonRedisSerializer<Signal> serializer = new Jackson2JsonRedisSerializer<>(Signal.class);
        RedisSerializationContext.RedisSerializationContextBuilder<String, Signal> builder =
                RedisSerializationContext.newSerializationContext(new StringRedisSerializer());
        RedisSerializationContext<String, Signal> context = builder.value(serializer).build();
        return new ReactiveRedisTemplate<>(factory, context);
    }

}