package it.pagopa.interop.signalhub.push.service.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

class PocGenericExceptionTest {

    @Test
    void getMessage() {
        assertDoesNotThrow(() -> new PocGenericException(ExceptionTypeEnum.SIGNALID_ALREADY_EXISTS, "message"));
        assertDoesNotThrow(() -> new PocGenericException(ExceptionTypeEnum.CORRESPONDENCE_NOT_FOUND, "message", HttpStatus.MULTI_STATUS));

    }

}