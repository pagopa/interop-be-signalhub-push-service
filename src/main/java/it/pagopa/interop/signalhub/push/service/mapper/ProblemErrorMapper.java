package it.pagopa.interop.signalhub.push.service.mapper;

import it.pagopa.interop.signalhub.push.service.dto.ProblemError;
import it.pagopa.interop.signalhub.push.service.dto.Signal;
import it.pagopa.interop.signalhub.push.service.dto.SignalRequest;
import it.pagopa.interop.signalhub.push.service.queue.model.SignalEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.validation.FieldError;

@Mapper(componentModel = "spring")
public interface ProblemErrorMapper {

    @Mapping(target = "code", source= "signal.field")
    @Mapping(target = "detail", source= "signal.defaultMessage")
    ProblemError toEvent(FieldError signal);



}
