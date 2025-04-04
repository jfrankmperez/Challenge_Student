package com.pruebatecnica.codeChallenge.application.studentcase;

import com.pruebatecnica.codeChallenge.application.dto.StudentDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface StudentUseCase {
    Flux<StudentDto> getAllStudentsUserCase ();
    Flux<StudentDto> getAllByStatusUserCase ();
    Mono<Void> saveStudent(StudentDto studentDto);
}
