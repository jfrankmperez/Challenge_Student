package com.pruebatecnica.CodeChallenge.application.studentcase;

import com.pruebatecnica.CodeChallenge.application.dto.StudentDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface StudentUseCase {
    Flux<StudentDto> getAllStudentsUserCase ();
    Flux<StudentDto> getAllByStatusUserCase ();
    Mono<Void> saveStudent(StudentDto studentDto);
}
