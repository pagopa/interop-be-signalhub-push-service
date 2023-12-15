package it.pagopa.interop.signalhub.push.service.auth;

import it.pagopa.interop.signalhub.push.service.config.SignalHubPushConfig;
import it.pagopa.interop.signalhub.push.service.generated.openapi.client.interop.model.v1.AgreementState;
import it.pagopa.interop.signalhub.push.service.utils.Const;
import jakarta.validation.constraints.AssertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PrincipalAgreementValidatorTest {

    @Mock
    private SignalHubPushConfig signalHubPushConfig;

    @InjectMocks
    private PrincipalAgreementValidator principalAgreementValidator;

    @Test
    void test() {
        PrincipalAgreement principalAgreement= new PrincipalAgreement();
        principalAgreement.setEServiceId(signalHubPushConfig.getId());
        principalAgreement.setState(AgreementState.ACTIVE.getValue());
        assertTrue(principalAgreementValidator.test(principalAgreement));
    }
}