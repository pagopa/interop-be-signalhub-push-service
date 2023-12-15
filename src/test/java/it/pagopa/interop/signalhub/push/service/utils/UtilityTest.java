package it.pagopa.interop.signalhub.push.service.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.pagopa.interop.signalhub.push.service.queue.model.SignalEvent;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;
class UtilityTest {

    @Test
    void getPrincipalFromSecurityContext() {
        assertNotNull(Utility.getPrincipalFromSecurityContext());
    }

    @Test
    void objectToJson() {
        assertNotNull(Utility.objectToJson(new ObjectMapper(), new SignalEvent()));
    }
}