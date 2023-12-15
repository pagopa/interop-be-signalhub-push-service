package it.pagopa.interop.signalhub.push.service.mapper;

import it.pagopa.interop.signalhub.push.service.dto.Signal;
import it.pagopa.interop.signalhub.push.service.dto.SignalRequest;
import it.pagopa.interop.signalhub.push.service.queue.model.SignalEvent;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
class SignalMapperTest {


    private SignalMapper service = Mappers.getMapper(SignalMapper.class);

    @Test
    void toEvent() {
        SignalRequest signalRequest= new SignalRequest();
        signalRequest.setSignalId(1L);
        SignalEvent signal= service.toEvent(signalRequest);
        assertEquals(signalRequest.getSignalId(), signal.getSignalId() );
    }

    @Test
    void whenCallToEventAndSignalRequestIsNull() {
        SignalRequest signalRequest= null;
        SignalEvent signal= service.toEvent(signalRequest);
        assertNull(signal);
    }

    @Test
    void toSignal() {
        SignalRequest signalRequest= new SignalRequest();
        signalRequest.setSignalId(1L);
        Signal signal= service.toSignal(signalRequest);
        assertEquals(signal.getSignalId(), signalRequest.getSignalId());
    }

    @Test
    void whenCallToSignalAndSignalRequestIsNull() {
        SignalRequest signalRequest= null;
        Signal signal= service.toSignal(signalRequest);
        assertNull(signal);
    }
}