package com.pruebatecnica.codeChallenge.infrastructure.adapter.rest;

import com.pruebatecnica.codeChallenge.application.dto.StudentDto;
import com.pruebatecnica.codeChallenge.infrastructure.adapter.exception.StudentFoundException;
import com.pruebatecnica.codeChallenge.infrastructure.adapter.service.StudentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.pruebatecnica.codeChallenge.application.StudentConstants.studentsConstants.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@WebFluxTest(StudentController.class)
public class StudentControllerTest {

    @Autowired
    private WebTestClient webTestClient;
    @MockBean
    private StudentService service;

    @Test
    void testGetAllStudentsByState_WhenStudentsExist() {
        StudentDto studentDtoTest = new StudentDto(1L, "Frank", "Moreno", "active", 20);
        
        when(service.getAllByStatusUserCase()).thenReturn(Flux.just(studentDtoTest));

        webTestClient.get()
                .uri(API_REQUEST+ "/active")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(StudentDto.class);


        verify(service, timeout(1)).getAllByStatusUserCase();
    }

    @Test
    void testGetAllStudentsByState_WhenNoStudentExist(){
        when(service.getAllByStatusUserCase()).thenReturn(Flux.error(new ResponseStatusException(HttpStatus.NOT_FOUND, NO_ACTIVE_FOUND_MESSAGE)));

        webTestClient.get()
                .uri(API_REQUEST+ "/active")
                .exchange()
                .expectStatus()
                .isNotFound()
                .expectBody()
                .jsonPath("$.message")
                .isEqualTo(NO_ACTIVE_FOUND_MESSAGE);

        verify(service, times(1)).getAllByStatusUserCase();
    }
    @Test
    void testSaveStudent_WhenValidStudent(){
        StudentDto studentDtoTest = new StudentDto(1L, "Frank", "Moreno", "active", 20);
        when(service.saveStudent(any(StudentDto.class))).thenReturn(Mono.empty());

        webTestClient.post()
                .uri(API_REQUEST)
                .bodyValue(studentDtoTest)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(void.class);

        verify(service, times(1)).saveStudent(any(StudentDto.class));
    }
    @Test
    void testSaveStudent_WhenStudentAlreadyExists(){
        StudentDto studentDtoTest = new StudentDto(1L, "Frank", "Moreno", "active", 20);
        when(service.saveStudent(any(StudentDto.class))).thenReturn(Mono.error(new StudentFoundException(String.format(STUDENT_FOUND_MESSAGE, studentDtoTest.getId()))));

        webTestClient.post()
                .uri(API_REQUEST)
                .bodyValue(studentDtoTest)
                .exchange()
                .expectStatus()
                .is4xxClientError()
                .expectBody()
                .jsonPath("$.message")
                .isEqualTo(String.format(STUDENT_FOUND_MESSAGE, studentDtoTest.getId()));

        verify(service, times(1)).saveStudent(any(StudentDto.class));
    }
}
