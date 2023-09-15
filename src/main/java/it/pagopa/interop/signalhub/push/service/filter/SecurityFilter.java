package it.pagopa.interop.signalhub.push.service.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;



@Slf4j
@Component
@AllArgsConstructor
public class SecurityFilter implements WebFilter {

    private static final String TYPE = "at+jwt";
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        // Estrazione del token Bearer dall'header di autenticazione
        String authorizationHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            // Rimuovere "Bearer " dal token
            String token = authorizationHeader.substring(7);
            if (token.isEmpty()){
                // gestire eccezione
            }
            DecodedJWT decodedJWT = JWT.decode(token);
            //String typ = decodedJWT.getClaim("typ").asString();
            //controlli sui parametri del token.
        }

        // Continua con la catena dei filtri
        return chain.filter(exchange);
    }
}
