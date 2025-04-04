package com.pruebatecnica.codeChallenge.application.mapper;

import com.pruebatecnica.codeChallenge.application.dto.StudentDto;
import com.pruebatecnica.codeChallenge.domain.model.Student;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    @Mapping(target = "id", ignore = true)
    Student toEntity (StudentDto studentDto);

    StudentDto toDto (Student student);
}
