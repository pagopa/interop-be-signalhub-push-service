package it.pagopa.interop.signalhub.push.service.queue.producer;

import it.pagopa.interop.signalhub.push.service.config.BaseTest;
import it.pagopa.interop.signalhub.push.service.queue.model.SignalEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class InternalSqsProducerTest extends BaseTest {



    @Autowired
    private InternalSqsProducer internalSqsProducer;

    @Test
    void push() {
       assertNotNull(internalSqsProducer.push(new SignalEvent()).block());
    }
}