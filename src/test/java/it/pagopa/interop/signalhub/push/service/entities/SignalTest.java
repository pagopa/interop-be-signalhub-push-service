package it.pagopa.interop.signalhub.push.service.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class SignalTest {

    private Signal signalEntity;

    @BeforeEach
    public void setUp() {
        signalEntity = new Signal();
    }

    @Test
    public void testId() {
        String id = "1";
        signalEntity.setId(id);
        assertEquals(id, signalEntity.getId());
    }
    @Test
    public void testSignalId() {
        Long signalId = 123L;
        signalEntity.setSignalId(signalId);
        assertEquals(signalId, signalEntity.getSignalId());
    }
    @Test
    public void testObjectId() {
        String objectId = "testObject";
        signalEntity.setObjectId(objectId);
        assertEquals(objectId, signalEntity.getObjectId());
    }

    @Test
    public void testEserviceId() {
        String eserviceId = "testEservice";
        signalEntity.setEserviceId(eserviceId);
        assertEquals(eserviceId, signalEntity.getEserviceId());
    }
    @Test
    public void testObjectType() {
        String objectType = "testType";
        signalEntity.setObjectType(objectType);
        assertEquals(objectType, signalEntity.getObjectType());
    }

    @Test
    public void testSignalType() {
        String signalType = "testSignal";
        signalEntity.setSignalType(signalType);
        assertEquals(signalType, signalEntity.getSignalType());
    }

    @Test
    void testTmstInsert() {
        Instant time = Instant.now();
        signalEntity.setTmstInsert(time);
        assertEquals(time, signalEntity.getTmstInsert());
    }

}
