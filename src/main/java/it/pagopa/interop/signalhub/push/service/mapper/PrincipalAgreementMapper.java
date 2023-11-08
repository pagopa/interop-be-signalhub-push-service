package it.pagopa.interop.signalhub.push.service.mapper;


import it.pagopa.interop.signalhub.push.service.auth.PrincipalAgreement;
import it.pagopa.interop.signalhub.push.service.generated.openapi.client.interop.model.v1.Agreement;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PrincipalAgreementMapper {

    PrincipalAgreement toPrincipal(Agreement agreement);


}
