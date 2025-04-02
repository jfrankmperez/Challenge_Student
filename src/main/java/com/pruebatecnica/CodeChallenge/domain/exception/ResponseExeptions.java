package com.pruebatecnica.CodeChallenge.domain.exception;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ResponseExeptions {
    private Integer code;
    private String error;
    private List<String> message;
}
