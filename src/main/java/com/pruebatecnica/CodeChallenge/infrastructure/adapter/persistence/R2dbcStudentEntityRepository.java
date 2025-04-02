package com.pruebatecnica.CodeChallenge.infrastructure.adapter.persistence;

import com.pruebatecnica.CodeChallenge.domain.model.Student;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface R2dbcStudentEntityRepository extends R2dbcRepository<Student, Long> {
    Flux<Student> findByStatus (String status);
}
