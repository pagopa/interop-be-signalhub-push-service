package it.pagopa.interop.signalhub.push.service.repository.cache.model;


import lombok.*;
import org.springframework.data.redis.core.RedisHash;

import java.sql.Timestamp;


@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("jwt")
public class JWTCache {
    private String jwt;
}
