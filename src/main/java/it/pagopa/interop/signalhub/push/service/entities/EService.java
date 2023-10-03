package it.pagopa.interop.signalhub.push.service.entities;


import it.pagopa.interop.signalhub.push.service.dto.Signal;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
@ToString
@Table("ORGANIZATION_ESERVICE")
public class EService implements RedisSerializer {

    @Id
    @Column("eservice_id")
    private String eserviceId;

    @Column("organization_id")
    private String organizationId;

    @Column("state")
    private String state;

    @Column("tmst_insert")
    private Timestamp tmstInsert;

    @Column("tmst_last_edit")
    private Timestamp tmstLastEdit;

    @Override
    public byte[] serialize(Object o) throws SerializationException {
        return new byte[0];
    }

    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        return null;
    }
}
