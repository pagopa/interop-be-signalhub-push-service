package it.pagopa.interop.signalhub.push.service.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JWTAuthSuccessHandlerTest {

    @InjectMocks
    private JWTAuthSuccessHandler jwtAuthSuccessHandler;

    @Mock
    private ServerWebExchange exchange;

    @Mock
    private WebFilterChain chain;



    @Test
    void onAuthenticationNotSuccess() {
        WebFilterExchange filterExchange = new WebFilterExchange(this.exchange, this.chain);
        Mockito.when(chain.filter(filterExchange.getExchange())).thenReturn(Mono.just("").then());
        assertNull(jwtAuthSuccessHandler.onAuthenticationSuccess(filterExchange, null).block());
    }

}