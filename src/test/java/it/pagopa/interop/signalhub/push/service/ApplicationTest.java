package it.pagopa.interop.signalhub.push.service;


import it.pagopa.interop.signalhub.push.service.config.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertTrue;


class ApplicationTest extends BaseTest {


    @Test
    void testApp(){
        assertTrue(true);
    }

}
