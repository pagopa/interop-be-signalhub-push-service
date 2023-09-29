package it.pagopa.interop.signalhub.push.service.auth;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Function;

import static it.pagopa.interop.signalhub.push.service.exception.ExceptionTypeEnum.*;

public class JWTConverter implements Function<ServerWebExchange, Mono<DecodedJWT>> {

    @Override
    public Mono<DecodedJWT> apply(ServerWebExchange serverWebExchange) {
        return Mono.justOrEmpty(serverWebExchange)
                .map(JWTUtil::getAuthorizationPayload)
                .filter(Objects::nonNull)
                .switchIfEmpty(Mono.error(new AccessDeniedException(JWT_NOT_PRESENT.getTitle())))
                .filter(JWTUtil.matchBearerLength())
                .switchIfEmpty(Mono.error(new AccessDeniedException(JWT_NOT_VALID.getTitle())))
                .map(JWTUtil.getBearerValue())
                .filter(token -> !token.isEmpty())
                .switchIfEmpty(Mono.error(new AccessDeniedException(JWT_EMPTY.getTitle())))
                .map(JWTUtil.decodeJwt())
                .filter(JWTUtil.matchType())
                .switchIfEmpty(Mono.error(new AccessDeniedException(JWT_TYPE_INCORRECT.getTitle())));
    }
}
