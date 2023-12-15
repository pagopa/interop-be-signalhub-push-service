package it.pagopa.interop.signalhub.push.service.mapper;

import it.pagopa.interop.signalhub.push.service.dto.Signal;
import it.pagopa.interop.signalhub.push.service.dto.SignalRequest;
import it.pagopa.interop.signalhub.push.service.queue.model.SignalEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SignalMapper {

    SignalEvent toEvent(SignalRequest signal);

    Signal toSignal(SignalRequest signalRequest);

}
