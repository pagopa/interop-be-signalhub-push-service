package it.pagopa.interop.signalhub.push.service.mapper;

import it.pagopa.interop.signalhub.push.service.queue.model.SignalEvent;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SignalMapper {

    SignalEvent toEvent();

}
