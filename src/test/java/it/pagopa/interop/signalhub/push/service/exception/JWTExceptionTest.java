package it.pagopa.interop.signalhub.push.service.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

class JWTExceptionTest {

    @Test
    void getMessage() {
        assertDoesNotThrow(() -> new JWTException(ExceptionTypeEnum.JWT_EMPTY   , "message", HttpStatus.BAD_REQUEST, "jwt"));
    }
}