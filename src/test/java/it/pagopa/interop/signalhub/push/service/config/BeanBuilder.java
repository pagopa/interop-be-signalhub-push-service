package it.pagopa.interop.signalhub.push.service.config;

import it.pagopa.interop.signalhub.push.service.auth.PrincipalAgreement;

public class BeanBuilder {

    public static PrincipalAgreement getPrincipal(){
        PrincipalAgreement p = new PrincipalAgreement();
        p.setPrincipalId("ABC-0009");
        p.setState("ACTIVE");
        p.setDescriptorId("VER-001");
        p.setPurposeId("PURPOSE-001");
        p.setProducerId("PAGO-PA-001");
        p.setEServiceId("PN-DIGITAL-001");
        return p;
    }

}
