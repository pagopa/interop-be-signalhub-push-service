package it.pagopa.interop.signalhub.push.service.queue.model;

import it.pagopa.interop.signalhub.push.service.dto.SignalType;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SignalEvent {

    private SignalType signalType;

    private String objectId;

    private String objectType;

    private String eserviceId;

    private Long indexSignal;

}
