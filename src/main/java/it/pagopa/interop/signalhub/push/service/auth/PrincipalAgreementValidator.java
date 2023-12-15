package it.pagopa.interop.signalhub.push.service.auth;

import it.pagopa.interop.signalhub.push.service.config.SignalHubPushConfig;
import it.pagopa.interop.signalhub.push.service.generated.openapi.client.interop.model.v1.AgreementState;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.function.Predicate;


@AllArgsConstructor
public class PrincipalAgreementValidator implements Predicate<PrincipalAgreement> {
    private final SignalHubPushConfig signalHubPushConfig;


    @Override
    public boolean test(PrincipalAgreement principalAgreement) {
        return StringUtils.equals(principalAgreement.getEServiceId(), signalHubPushConfig.getId()) &&
                StringUtils.equals(principalAgreement.getState(), AgreementState.ACTIVE.getValue());
    }



}
