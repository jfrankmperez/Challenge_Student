package com.pruebatecnica.codeChallenge.infrastructure.adapter.service;

import com.pruebatecnica.codeChallenge.application.dto.StudentDto;
import com.pruebatecnica.codeChallenge.application.mapper.StudentMapper;
import com.pruebatecnica.codeChallenge.application.studentcase.StudentUseCase;
import com.pruebatecnica.codeChallenge.infrastructure.adapter.exception.StudentFoundException;
import com.pruebatecnica.codeChallenge.infrastructure.adapter.persistence.R2dbcStudentEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Locale;

import static com.pruebatecnica.codeChallenge.application.StudentConstants.studentsConstants.NO_ACTIVE_FOUND_MESSAGE;
import static com.pruebatecnica.codeChallenge.application.StudentConstants.studentsConstants.NO_STUDENTS_FOUND_MESSAGE;
import static com.pruebatecnica.codeChallenge.application.StudentConstants.studentsConstants.STUDENT_FOUND_MESSAGE;
import static com.pruebatecnica.codeChallenge.application.enums.StudentEnums.ACTIVE;

@Service
@RequiredArgsConstructor
public class StudentService implements StudentUseCase {

    private final R2dbcStudentEntityRepository repository;
    private final StudentMapper studentMapper;

   @Override
   public Mono<Void> saveStudent(StudentDto studentDto) {
       return repository.findById(studentDto.getId())
               .flatMap(
                       studentExists -> Mono.error(
                               new StudentFoundException(String.format(STUDENT_FOUND_MESSAGE,studentDto.getId()))))
               .switchIfEmpty(Mono.defer(() -> repository.save(studentMapper.toEntity(studentDto))))
               .then();
   }

    @Override
    public Flux<StudentDto> getAllStudentsUserCase() {
        return repository.findAll()
                .map(studentMapper::toDto)
                .switchIfEmpty(Flux.error(new ResponseStatusException(HttpStatus.NOT_FOUND,NO_STUDENTS_FOUND_MESSAGE )));
    }

    @Override
    public Flux<StudentDto> getAllByStatusUserCase() {
        return repository.findByStatus(ACTIVE.toString().toLowerCase(Locale.ROOT))
                .map(studentMapper::toDto)
                .switchIfEmpty(Flux.error(new ResponseStatusException(HttpStatus.NOT_FOUND, NO_ACTIVE_FOUND_MESSAGE)));
    }
}
