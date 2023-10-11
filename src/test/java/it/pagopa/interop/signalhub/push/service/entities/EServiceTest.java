package it.pagopa.interop.signalhub.push.service.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class EServiceTest {
    private EService eService;

    @BeforeEach
    public void setUp() {
        eService = new EService();
    }

    @Test
    public void testEServiceId() {
        String eserviceId = "1";
        eService.setEserviceId(eserviceId);
        assertEquals(eserviceId, eService.getEserviceId());
    }
    @Test
    public void testProducerId() {
        String producerId = "1";
        eService.setProducerId(producerId);
        assertEquals(producerId, eService.getProducerId());
    }
    @Test
    public void testState() {
        String state = "CREATE";
        eService.setState(state);
        assertEquals(state, eService.getState());
    }

    @Test
    public void testTmstLastEdit() {
        Timestamp tmstLastEdit = Timestamp.from(Instant.now());
        eService.setTmstLastEdit(tmstLastEdit);
        assertEquals(tmstLastEdit, eService.getTmstLastEdit());
    }

    @Test
    public void testTmstInsert() {
        Timestamp tmstInsert = Timestamp.from(Instant.now());
        eService.setTmstInsert(tmstInsert);
        assertEquals(tmstInsert, eService.getTmstInsert());
    }

}