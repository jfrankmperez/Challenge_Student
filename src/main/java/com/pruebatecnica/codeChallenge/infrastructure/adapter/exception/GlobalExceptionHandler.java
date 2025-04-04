package com.pruebatecnica.codeChallenge.infrastructure.adapter.exception;

import com.pruebatecnica.codeChallenge.domain.exception.ResponseExeptions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<ResponseEntity<ResponseExeptions>> validationException(WebExchangeBindException ex){
        BindingResult bindingResult = ex.getBindingResult();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        List<String> listMessage = bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.toList());
        ResponseExeptions response = ResponseExeptions.builder()
                .code(status.value())
                .error(status.getReasonPhrase())
                .message(listMessage)
                .build();
        return  Mono.just(new ResponseEntity<>(response, status));
    }

    @ExceptionHandler(StudentFoundException.class)
    public Mono<ResponseEntity<ResponseExeptions>> studentFoundException(StudentFoundException ex) {
        HttpStatus status = HttpStatus.CONFLICT;
        ResponseExeptions response = ResponseExeptions.builder()
                .code(status.value())
                .error(status.getReasonPhrase())
                .message(Collections.singletonList(ex.getMessage()))
                .build();
        return Mono.just(new ResponseEntity<>(response,status));
    }

    @ExceptionHandler(ResponseStatusException.class)
    public Mono<ResponseEntity<ResponseExeptions>> responseStatusException(ResponseStatusException ex) {
        HttpStatus status = ex.getStatus();
        ResponseExeptions response = ResponseExeptions.builder()
                .code(status.value())
                .error(status.getReasonPhrase())
                .message(Collections.singletonList(ex.getReason()))
                .build();
        return Mono.just(new ResponseEntity<>(response,status));
    }

}