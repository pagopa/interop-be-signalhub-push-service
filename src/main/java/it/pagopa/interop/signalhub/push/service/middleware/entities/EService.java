package it.pagopa.interop.signalhub.push.service.middleware.entities;


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
@Table("ESERVICE")
public class EService implements Serializable {

    @Id
    @Column("eserviceId")
    private String eserviceId;

    @Column("organizationId")
    private String organizationId;

    @Column("state")
    private String state;

    @Column("tmstInsert")
    private Timestamp tmstInsert;

    @Column("tmstLastEdit")
    private Timestamp tmstLastEdit;

}
