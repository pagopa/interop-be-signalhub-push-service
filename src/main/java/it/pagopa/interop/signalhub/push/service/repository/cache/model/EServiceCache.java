package it.pagopa.interop.signalhub.push.service.repository.cache.model;


import lombok.*;
import org.springframework.data.redis.core.RedisHash;

import java.sql.Timestamp;



@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("eservices")
public class EServiceCache {
    private String eserviceId;
    private String producerId;
    private String descriptorId;
    private String state;
    private Timestamp tmstInsert;
    private Timestamp tmstLastEdit;
}
