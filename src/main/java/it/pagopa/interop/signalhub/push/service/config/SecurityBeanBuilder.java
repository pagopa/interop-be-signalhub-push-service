package it.pagopa.interop.signalhub.push.service.config;

import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.JwkProviderBuilder;
import it.pagopa.interop.signalhub.push.service.auth.JWTAuthManager;
import it.pagopa.interop.signalhub.push.service.auth.JWTConverter;
import it.pagopa.interop.signalhub.push.service.auth.PrincipalAgreementValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;

@Configuration
public class SecurityBeanBuilder {

    @Value("${jwk.provider.endpoint}")
    private String jwkProviderEndpoint;

    @Bean
    public JwkProvider jwkProvider(){
        return new JwkProviderBuilder(jwkProviderEndpoint)
                .cached(true)
                .build();
    }

    @Bean
    public JWTConverter getJwtConverter(JwkProvider jwkProvider, SignalHubPushConfig signalHubPushConfig){
        return new JWTConverter(jwkProvider, signalHubPushConfig);
    }

    @Bean
    public PrincipalAgreementValidator getPrincipalAgreementValidator(SignalHubPushConfig signalHubPushConfig){
        return new PrincipalAgreementValidator(signalHubPushConfig);
    }

    @Bean
    public ReactiveAuthenticationManager getReactiveAuthManager() {
        return new JWTAuthManager();
    }


}
