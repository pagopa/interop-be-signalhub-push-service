package it.pagopa.interop.signalhub.push.service.exception;

import com.fasterxml.jackson.databind.JsonMappingException;
import it.pagopa.interop.signalhub.push.service.dto.Problem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import reactor.core.publisher.Mono;


@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(JsonMappingException.class)
    public void handle(JsonMappingException e) {
        log.error("Returning HTTP 400 Bad Request {}", e.getMessage());
    }

    @ExceptionHandler(PDNDGenericException.class)
    public Mono<ResponseEntity<Problem>> handleResponseEntityException(final PDNDGenericException exception){
        log.warn(exception.toString());
        final Problem problem = new Problem();
        problem.setTitle(exception.getExceptionType().getTitle());
        problem.setDetail(exception.getMessage());
        problem.setStatus(exception.getHttpStatus().value());
        return Mono.just(ResponseEntity.status(exception.getHttpStatus()).body(problem));
    }

}
