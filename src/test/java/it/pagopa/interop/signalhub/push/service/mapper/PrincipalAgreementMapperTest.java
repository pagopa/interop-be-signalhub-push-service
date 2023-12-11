package it.pagopa.interop.signalhub.push.service.mapper;

import it.pagopa.interop.signalhub.push.service.auth.PrincipalAgreement;
import it.pagopa.interop.signalhub.push.service.cache.model.EServiceCache;
import it.pagopa.interop.signalhub.push.service.entities.EService;
import it.pagopa.interop.signalhub.push.service.generated.openapi.client.interop.model.v1.Agreement;
import org.junit.jupiter.api.Test;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


@Mapper(componentModel = "spring")
class PrincipalAgreementMapperTest {

    private PrincipalAgreementMapper principalAgreementMapper = Mappers.getMapper(PrincipalAgreementMapper.class);


    @Test
    void toPrincipal() {
        Agreement agreement= new Agreement();
        agreement.setEserviceId(UUID.randomUUID());
        agreement.setConsumerId(UUID.randomUUID());
        PrincipalAgreement principalAgreement= principalAgreementMapper.toPrincipal(agreement);
        assertEquals(principalAgreement.getEServiceId(), agreement.getEserviceId().toString() );
    }
}