package com.pruebatecnica.CodeChallenge.application.mapper;

import com.pruebatecnica.CodeChallenge.application.dto.StudentDto;
import com.pruebatecnica.CodeChallenge.domain.model.Student;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    @Mapping(target = "id", ignore = true)
    Student toEntity (StudentDto studentDto);

    StudentDto toDto (Student student);
}
