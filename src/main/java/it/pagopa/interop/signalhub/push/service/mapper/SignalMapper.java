package it.pagopa.interop.signalhub.push.service.mapper;

import it.pagopa.interop.signalhub.push.service.dto.Signal;
import it.pagopa.interop.signalhub.push.service.dto.SignalRequest;
import it.pagopa.interop.signalhub.push.service.entities.EService;
import it.pagopa.interop.signalhub.push.service.queue.model.SignalEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface SignalMapper {

    SignalEvent toEvent(SignalRequest signal);

    @Mapping(target = "signalId", source= "signalRequest.indexSignal")
    Signal toSignal(SignalRequest signalRequest);

}
