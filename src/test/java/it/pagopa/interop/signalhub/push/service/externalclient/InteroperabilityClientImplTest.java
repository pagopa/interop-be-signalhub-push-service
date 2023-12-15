package it.pagopa.interop.signalhub.push.service.externalclient;


import it.pagopa.interop.signalhub.push.service.config.BaseTest;
import it.pagopa.interop.signalhub.push.service.generated.openapi.client.interop.model.v1.Agreement;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import static org.junit.jupiter.api.Assertions.*;


class InteroperabilityClientImplTest extends BaseTest.WithMockServer {

    private static final String PURPOSE_OK = "9a7e5371-0832-4301-9d97-d762f703dd78";

    @Autowired
    private InteroperabilityClient interoperabilityClient;



    @Test
    void whenRetrieveByPurposeIdThenReturnPrincipalAgreementDetail(){
        Agreement agreement = interoperabilityClient.getAgreementByPurposeId(PURPOSE_OK).block();
        assertNotNull(agreement);
        assertEquals(PURPOSE_OK, agreement.getId().toString());
    }



}
