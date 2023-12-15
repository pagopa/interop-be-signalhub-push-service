package it.pagopa.interop.signalhub.push.service.config;

import it.pagopa.interop.signalhub.push.service.auth.PrincipalAgreement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;

@Slf4j
@Configuration
public class BeanBuilder {


    @Bean
    public MockServer getMockServer(@Value("${mockserver.bean.port}") int port){
        log.info("Port :  {}", port);
        return new MockServer(port);
    }

    public static PublicKey getPublicKey() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        return keyPairGenerator.generateKeyPair().getPublic();
    }

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

    public static ClientRegistration.Builder clientCredentials() {
        return ClientRegistration.withRegistrationId("client-credentials")
                .tokenUri("http://test.provider.com")
                .registrationId("client-credentials")
                .clientId("client-id")
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS);
    }


}
