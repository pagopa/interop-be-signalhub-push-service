package it.pagopa.interop.signalhub.push.service.entities;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class EServiceKey implements Serializable {
    private String eserviceId;
    private String producerId;
}