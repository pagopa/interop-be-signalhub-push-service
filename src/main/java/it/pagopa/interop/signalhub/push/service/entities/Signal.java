package it.pagopa.interop.signalhub.push.service.entities;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
@ToString
@Table("SIGNAL")
public class Signal implements Serializable {

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

}
