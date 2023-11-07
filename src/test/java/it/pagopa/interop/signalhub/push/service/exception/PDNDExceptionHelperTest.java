package it.pagopa.interop.signalhub.push.service.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PDNDExceptionHelperTest {

    @Test
    void handle() {
        PDNDExceptionHelper pdndExceptionHelper= new PDNDExceptionHelper();
        assertNotNull(pdndExceptionHelper.handle(new Throwable()));
        assertNotNull(pdndExceptionHelper.handle(new PDNDGenericException(ExceptionTypeEnum.CORRESPONDENCE_NOT_FOUND, ExceptionTypeEnum.CORRESPONDENCE_NOT_FOUND.getMessage())));
    }

    @Test
    void generateFallbackProblem() {
        PDNDExceptionHelper pdndExceptionHelper= new PDNDExceptionHelper();
        assertNotNull(pdndExceptionHelper.generateFallbackProblem());
    }
}