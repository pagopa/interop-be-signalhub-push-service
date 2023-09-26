package it.pagopa.interop.signalhub.push.service.filter;

import com.auth0.jwk.Jwk;
import com.auth0.jwk.JwkException;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwt.interfaces.DecodedJWT;
import it.pagopa.interop.signalhub.push.service.auth.JWTAuthManager;
import it.pagopa.interop.signalhub.push.service.auth.JWTConverter;
import it.pagopa.interop.signalhub.push.service.auth.JWTUtil;
import it.pagopa.interop.signalhub.push.service.exception.PnGenericException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotNull;
import java.security.PublicKey;
import java.util.function.Function;

import static it.pagopa.interop.signalhub.push.service.exception.ExceptionTypeEnum.*;

@Profile("!test")
@Slf4j
@Configuration
public class JWTFilter implements WebFilter {
    private final Function<ServerWebExchange, Mono<DecodedJWT>> jwtDecoded = new JWTConverter();
    private final ReactiveAuthenticationManager reactiveAuthManager = new JWTAuthManager();
    private final ServerSecurityContextRepository securityContextRepository = NoOpServerSecurityContextRepository.getInstance();

    @Autowired
    private ServerAuthenticationSuccessHandler authSuccessHandler;
    @Autowired
    private JwkProvider jwkProvider;



    @Override
    public Mono<Void> filter(ServerWebExchange exchangeRequest, @NotNull WebFilterChain chain) {

        return Mono.justOrEmpty(exchangeRequest)
                .doOnNext(exchange -> log.info("Start JWT filter validation"))
                .flatMap(exchange -> jwtDecoded.apply(exchangeRequest))
                .doOnNext(jwt -> log.info("Jwt decoded"))
                .switchIfEmpty(chain.filter(exchangeRequest).then(Mono.empty()))
                .doOnNext(jwt -> log.info("JWT is valid ?"))
                .map(JWTUtil.verifyToken(this::getPublicKey))
                .doOnNext(jwt -> log.info("JWT is valid"))
                .flatMap(token -> authenticate(exchangeRequest, chain, token));


    }

    private Mono<Void> authenticate(ServerWebExchange exchange,
                                    WebFilterChain chain, DecodedJWT token) {
        WebFilterExchange webFilterExchange = new WebFilterExchange(exchange, chain);
        return this.reactiveAuthManager.authenticate(JWTUtil.getAuthenticationJwt(token))
                .flatMap(authentication -> onAuthSuccess(authentication, webFilterExchange));
    }

    private Mono<Void> onAuthSuccess(Authentication authentication, WebFilterExchange webFilterExchange) {
        ServerWebExchange exchange = webFilterExchange.getExchange();
        SecurityContextImpl securityContext = new SecurityContextImpl();
        securityContext.setAuthentication(authentication);
        return this.securityContextRepository.save(exchange, securityContext)
                .then(this.authSuccessHandler.onAuthenticationSuccess(webFilterExchange, authentication))
                .contextWrite(ReactiveSecurityContextHolder.withSecurityContext(Mono.just(securityContext)));
    }


    private PublicKey getPublicKey(DecodedJWT jwt) {
        try {
            Jwk jwk = jwkProvider.get(jwt.getKeyId());
            return jwk.getPublicKey();
        } catch (JwkException ex) {
            throw new PnGenericException(JWT_NOT_VALID, JWT_NOT_VALID.getMessage(), HttpStatus.UNAUTHORIZED);
        }

    }


}

