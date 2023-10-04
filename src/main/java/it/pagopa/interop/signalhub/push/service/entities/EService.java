package it.pagopa.interop.signalhub.push.service.entities;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import java.sql.Timestamp;

@Getter
@Setter
@ToString
@Table("ORGANIZATION_ESERVICE")
public class EService {
    public static final String COLUMN_ESERVICE_ID = "eservice_id";
    public static final String COLUMN_ORGANIZATION_ID = "organization_id";
    public static final String COLUMN_STATE = "state";
    public static final String COLUMN_DATE_INSERT = "tmst_insert";
    public static final String COLUMN_DATE_UPDATE = "tmst_last_edit";

    @Id
    @Column(COLUMN_ESERVICE_ID)
    private String eserviceId;

    @Column(COLUMN_ORGANIZATION_ID)
    private String organizationId;

    @Column(COLUMN_STATE)
    private String state;

    @Column(COLUMN_DATE_INSERT)
    private Timestamp tmstInsert;

    @Column(COLUMN_DATE_UPDATE)
    private Timestamp tmstLastEdit;

}
