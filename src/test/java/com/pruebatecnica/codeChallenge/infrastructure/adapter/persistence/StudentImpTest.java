package com.pruebatecnica.codeChallenge.infrastructure.adapter.persistence;

import com.pruebatecnica.codeChallenge.application.dto.StudentDto;
import com.pruebatecnica.codeChallenge.application.mapper.StudentMapper;
import com.pruebatecnica.codeChallenge.domain.model.Student;
import com.pruebatecnica.codeChallenge.infrastructure.adapter.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static com.pruebatecnica.codeChallenge.application.StudentConstants.studentsConstants.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StudentImpTest {

    @InjectMocks
    private StudentService service;
    @Mock
    private StudentMapper mapper;
    @Mock
    private R2dbcStudentEntityRepository repository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        this.service = new StudentService(repository, mapper);
    }

    @Test
    void testSaveStudent() {
        StudentDto studentDtoTest = new StudentDto(3L, "Frank", "Moreno", "active", 20);
        Student studentTest = new Student(1L, "Frank", "Moreno", "active", 20);

        when(repository.findById(studentDtoTest.getId())).thenReturn(Mono.empty());
        when(mapper.toEntity(any(StudentDto.class))).thenReturn(studentTest);
        when(repository.save(studentTest)).thenReturn(Mono.just(studentTest));

        StepVerifier.create(service.saveStudent(studentDtoTest))
                .verifyComplete();

        verify(repository, timeout(1)).findById(studentDtoTest.getId());
        verify(repository, timeout(1)).save(studentTest);
    }

    @Test
    void testDontSaveStudent (){
        StudentDto studentDtoTest = new StudentDto(1L, "Frank", "Moreno", "active", 20);
        Student studentTest = new Student(1L, "Frank", "Moreno", "active", 20);
        when(repository.findById(anyLong())).thenReturn(Mono.just(studentTest));

        StepVerifier.create(service.saveStudent(studentDtoTest))
                .expectErrorMessage(String.format(STUDENT_FOUND_MESSAGE,studentDtoTest.getId()))
                .verify();

        verify(repository, timeout(1)).findById(studentDtoTest.getId());
        verify(repository, never()).save(any());
    }

    @Test
    void testGetAllStudentByStatusActivo_okCase(){
        Student studentTest = new Student(1L, "Frank", "Moreno", "active", 20);
        Student studentTest2 = new Student(2L, "Jhon", "Perez", "active", 20);
        StudentDto studentDtoTest = new StudentDto(1L, "Frank", "Moreno", "active", 20);
        StudentDto studentDtoTest2 = new StudentDto(2L, "John", "Perez", "active", 20);

        when(repository.findByStatus("active")).thenReturn(Flux.just(studentTest,studentTest2));
        when(mapper.toDto(studentTest)).thenReturn(studentDtoTest);
        when(mapper.toDto(studentTest2)).thenReturn(studentDtoTest2);

        StepVerifier.create(service.getAllByStatusUserCase())
                .expectNext(studentDtoTest)
                .expectNext(studentDtoTest2)
                .verifyComplete();

        verify(repository, timeout(1)).findByStatus("active");
    }

    @Test
    void testGetAllStudentByStateActive_badCase(){
        when(repository.findByStatus("active")).thenReturn(Flux.empty());

        StepVerifier.create(service.getAllByStatusUserCase())
                .expectError(ResponseStatusException.class)
                .verify();

        verify(repository, timeout(1)).findByStatus("active");
    }

}
