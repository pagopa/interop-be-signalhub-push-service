package it.pagopa.interop.signalhub.push.service.exception;

import com.fasterxml.jackson.databind.JsonMappingException;
import it.pagopa.interop.signalhub.push.service.dto.Problem;
import it.pagopa.interop.signalhub.push.service.dto.ProblemError;
import it.pagopa.interop.signalhub.push.service.mapper.ProblemErrorMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler {

    @Autowired
    private ProblemErrorMapper problemErrorMapper;

    @ExceptionHandler(JsonMappingException.class)
    public Mono<ResponseEntity<Problem>> handleJsonMappingException(JsonMappingException exception) {
        log.error("Returning HTTP 400 Bad Request {}", exception.getMessage());
        final Problem problem = new Problem();
        problem.setTitle(HttpStatus.BAD_REQUEST.name());
        problem.setDetail(HttpStatus.valueOf(HttpStatus.BAD_REQUEST.value()).getReasonPhrase());
        problem.setStatus(HttpStatus.BAD_REQUEST.value());

        ProblemError problemError= new ProblemError();
        problemError.setCode("INVALID_INPUT");
        problemError.setDetail(exception.getCause()!= null ? exception.getCause().getMessage() : exception.getMessage());

        List<ProblemError> errors= new ArrayList<>();
        errors.add(problemError);

        problem.setErrors(errors);

        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(problem));

    }

    @ExceptionHandler(PDNDGenericException.class)
    public Mono<ResponseEntity<Problem>> handleResponseEntityException(final PDNDGenericException exception){
        log.warn(exception.toString());
        final Problem problem = new Problem();
        if (exception.getExceptionType() == ExceptionTypeEnum.SIGNALID_ALREADY_EXISTS) {
            problem.setTitle(HttpStatus.BAD_REQUEST.name());
            problem.setDetail(HttpStatus.valueOf(HttpStatus.BAD_REQUEST.value()).getReasonPhrase());
        } else {
            problem.setTitle(HttpStatus.FORBIDDEN.name());
            problem.setDetail(HttpStatus.valueOf(HttpStatus.FORBIDDEN.value()).getReasonPhrase());
        }

        problem.setStatus(exception.getHttpStatus().value());

        ProblemError problemError= new ProblemError();
        problemError.setCode(exception.getExceptionType().getTitle());
        problemError.setDetail(exception.getMessage());

        List<ProblemError> errors= new ArrayList<>();
        errors.add(problemError);

        problem.setErrors(errors);

        return Mono.just(ResponseEntity.status(exception.getHttpStatus()).body(problem));
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<ResponseEntity<Problem>> handleWebExchangeBindException(final WebExchangeBindException exception){
        log.warn(exception.toString());

        List<ProblemError> errors= new ArrayList<>();
        for(FieldError error : exception.getFieldErrors()) {
            errors.add(problemErrorMapper.toProblemError(error));
        }

        final Problem problem = new Problem();
        problem.setTitle(HttpStatus.valueOf(exception.getStatusCode().value()).name());
        problem.setDetail(HttpStatus.valueOf(exception.getStatusCode().value()).getReasonPhrase());
        problem.setStatus(exception.getStatusCode().value());
        problem.setErrors(errors);

        return Mono.just(ResponseEntity.status(exception.getStatusCode()).body(problem));
    }



}
