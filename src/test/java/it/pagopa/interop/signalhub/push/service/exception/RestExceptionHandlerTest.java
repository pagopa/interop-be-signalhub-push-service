package it.pagopa.interop.signalhub.push.service.exception;

import com.fasterxml.jackson.databind.JsonMappingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;


import java.lang.reflect.Method;
import java.util.HashMap;

import static it.pagopa.interop.signalhub.push.service.exception.ExceptionTypeEnum.*;
import static org.mockito.Mockito.mock;


class RestExceptionHandlerTest {
    @Spy
    private RestExceptionHandler restExceptionHandler;


    @BeforeEach
    void setUp(){
        this.initialize();
    }

    @Test
    void handleResponseEntityExceptionTest(){
        PDNDGenericException pnGenericException = new PDNDGenericException(UNAUTHORIZED, UNAUTHORIZED.getMessage());
        restExceptionHandler.handleResponseEntityException(pnGenericException)
                .map(responseEntity -> {
                    Assertions.assertEquals(HttpStatus.FORBIDDEN.name(), responseEntity.getBody().getTitle());
                    Assertions.assertEquals(HttpStatus.valueOf(HttpStatus.FORBIDDEN.value()).getReasonPhrase(), responseEntity.getBody().getDetail());
                    return Mono.empty();
                })
                .block();
    }


    @Test
    void handleJsonMappingException(){
        JsonMappingException jsonMappingException = new JsonMappingException("message");
        restExceptionHandler.handleJsonMappingException(jsonMappingException)
                .map(responseEntity -> {
                    Assertions.assertEquals(HttpStatus.BAD_REQUEST.name(), responseEntity.getBody().getTitle());
                    Assertions.assertEquals(HttpStatus.valueOf(HttpStatus.BAD_REQUEST.value()).getReasonPhrase(), responseEntity.getBody().getDetail());
                    return Mono.empty();
                })
                .block();
    }

    private void initialize() {
        restExceptionHandler = new RestExceptionHandler();
    }
}
