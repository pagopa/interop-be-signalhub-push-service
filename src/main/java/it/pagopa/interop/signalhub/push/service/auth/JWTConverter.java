package it.pagopa.interop.signalhub.push.service.auth;

import com.auth0.jwt.interfaces.DecodedJWT;
import it.pagopa.interop.signalhub.push.service.exception.PDNDGenericException;
import org.springframework.http.HttpStatus;
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
                .switchIfEmpty(Mono.error(new PDNDGenericException(JWT_NOT_PRESENT, JWT_NOT_PRESENT.getMessage(), HttpStatus.UNAUTHORIZED)))
                .filter(JWTUtil.matchBearerLength())
                .switchIfEmpty(Mono.error(new PDNDGenericException(JWT_NOT_PRESENT, JWT_NOT_PRESENT.getMessage(), HttpStatus.UNAUTHORIZED)))
                .map(JWTUtil.getBearerValue())
                .filter(token -> !token.isEmpty())
                .switchIfEmpty(Mono.error(new PDNDGenericException(JWT_NOT_PRESENT, JWT_NOT_PRESENT.getMessage(), HttpStatus.UNAUTHORIZED)))
                .map(JWTUtil.decodeJwt())
                .filter(JWTUtil.matchType())
                .switchIfEmpty(Mono.error(new PDNDGenericException(JWT_NOT_PRESENT, JWT_NOT_PRESENT.getMessage(), HttpStatus.UNAUTHORIZED)));
    }
}
