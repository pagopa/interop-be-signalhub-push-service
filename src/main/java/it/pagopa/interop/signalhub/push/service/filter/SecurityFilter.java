package it.pagopa.interop.signalhub.push.service.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import it.pagopa.interop.signalhub.push.service.config.SignalHubPushConfig;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Date;



@Slf4j
@Component
@AllArgsConstructor
public class SecurityFilter implements WebFilter {

    private final SignalHubPushConfig signalHubPushConfig;
    private static final String TYPE = "at+jwt";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
            // Estrazione del token Bearer dall'header di autenticazione
        String authorizationHeader = exchange.getRequest()
                .getHeaders()
                .getFirst("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            // Rimuovere "Bearer " dal token
            String token = authorizationHeader.substring(7);
            if (token.isEmpty()){
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
            DecodedJWT decodedJWT = JWT.decode(token);
            if (!verifyTokenParam(decodedJWT)){
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
        }else {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        return chain.filter(exchange);
    }

    private boolean verifyTokenParam(DecodedJWT decodedJWT) {
        boolean typ = decodedJWT.getClaim("typ").asString().equalsIgnoreCase(TYPE);
        boolean iss = decodedJWT.getClaim("iss").asString().equalsIgnoreCase(signalHubPushConfig.getIssuerVuocher());
        Date convertJwtDate = new Date(decodedJWT.getClaim("exp").asInt()*1000);
        boolean exp = convertJwtDate.after(Date.from(Instant.now()));
        boolean aud = decodedJWT.getClaim("aud").asString().equalsIgnoreCase("");

        return typ && iss && exp && aud;
    }


}
