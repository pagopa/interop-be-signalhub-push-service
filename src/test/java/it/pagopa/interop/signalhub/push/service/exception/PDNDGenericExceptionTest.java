package it.pagopa.interop.signalhub.push.service.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

class PDNDGenericExceptionTest {

    @Test
    void getMessage() {
        assertDoesNotThrow(() -> new PDNDGenericException(ExceptionTypeEnum.SIGNALID_ALREADY_EXISTS, "message"));
        assertDoesNotThrow(() -> new PDNDGenericException(ExceptionTypeEnum.CORRESPONDENCE_NOT_FOUND, "message", HttpStatus.MULTI_STATUS));

    }

}