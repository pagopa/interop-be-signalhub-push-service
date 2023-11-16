package it.pagopa.interop.signalhub.push.service.entities;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.Objects;


@Getter
@Setter
@ToString
@Table("ESERVICE")
public class EService{
    public static final String COLUMN_ESERVICE_ID = "eservice_id";
    public static final String COLUMN_PRODUCER_ID = "producer_id";
    public static final String COLUMN_DESCRIPTOR_ID = "descriptor_id";
    public static final String COLUMN_EVENT_ID = "event_id";
    public static final String COLUMN_STATE = "state";
    public static final String COLUMN_DATE_INSERT = "tmst_insert";
    public static final String COLUMN_DATE_UPDATE = "tmst_last_edit";

    @Column(COLUMN_ESERVICE_ID)
    private String eserviceId;

    @Column( COLUMN_PRODUCER_ID)
    private String producerId;

    @Column(COLUMN_DESCRIPTOR_ID)
    private String descriptorId;

    @Column(COLUMN_EVENT_ID)
    private Long eventId;

    @Column(COLUMN_STATE)
    private String state;

    @Column(COLUMN_DATE_INSERT)
    private Instant tmstInsert;

    @Column(COLUMN_DATE_UPDATE)
    private Instant tmstLastEdit;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EService eService = (EService) o;
        return Objects.equals(eserviceId, eService.eserviceId) && Objects.equals(producerId, eService.producerId) && Objects.equals(descriptorId, eService.descriptorId) && Objects.equals(eventId, eService.eventId) && Objects.equals(state, eService.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eserviceId, producerId, descriptorId, eventId, state);
    }
}