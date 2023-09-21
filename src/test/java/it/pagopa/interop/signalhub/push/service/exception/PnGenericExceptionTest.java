package it.pagopa.interop.signalhub.push.service.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

class PnGenericExceptionTest {

    @Test
    void getMessage() {
        assertDoesNotThrow(() -> new PnGenericException(ExceptionTypeEnum.SIGNALID_ALREADY_EXISTS, "message"));
        assertDoesNotThrow(() -> new PnGenericException(ExceptionTypeEnum.CORRESPONDENCE_NOT_FOUND, "message", HttpStatus.MULTI_STATUS));

    }

}