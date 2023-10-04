package it.pagopa.interop.signalhub.push.service.entities;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
@ToString
@Table("SIGNAL")
//@RedisHash("signal")
public class Signal implements RedisSerializer {

    @Id
    @Column("id")
    private String id;

    @Column("eservice_id")
    private String eserviceId;

    @Column("signal_Id")
    private Long signalId;

    @Column("object_id")
    private String objectId;

    @Column("object_type")
    private String objectType;

    @Column("signal_type")
    private String signalType;

    @Column("tmst_insert")
    private Timestamp tmstInsert;

    @Override
    public byte[] serialize(Object o) throws SerializationException {
        return new byte[0];
    }

    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        return null;
    }
}
