package com.pruebatecnica.codeChallenge.infrastructure.adapter.rest;

import com.pruebatecnica.codeChallenge.application.dto.StudentDto;
import com.pruebatecnica.codeChallenge.infrastructure.adapter.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.pruebatecnica.codeChallenge.application.StudentConstants.studentsConstants.API_REQUEST;

@RestController
@RequestMapping(API_REQUEST)
@RequiredArgsConstructor
public class StudentController {

   private final StudentService studentService;

    @GetMapping("/active")
    public Flux<StudentDto> getAllStudentsByState (){
        return studentService.getAllByStatusUserCase();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> saveStudent (@RequestBody @Validated StudentDto studentDto){
        return studentService.saveStudent(studentDto);
    }

    @GetMapping
    public Flux<StudentDto> getAllStudents (){
        return studentService.getAllStudentsUserCase();
    }
}
