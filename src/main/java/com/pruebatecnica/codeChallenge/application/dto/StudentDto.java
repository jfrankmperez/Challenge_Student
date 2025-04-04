package com.pruebatecnica.codeChallenge.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto{

    @NotNull(message = "ID cannot be null")
    @Min(value = 1, message = "ID must be greater than or equal to 1")
    private Long id;


    @NotBlank(message = "Name cannot be empty or null")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Name must contain only letters")
    private String name;


    @NotBlank(message = "Last name cannot be empty or null")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Last name must contain only letters")
    private String lastname;


    @NotBlank(message = "Status cannot be empty or null")
    @Pattern(regexp = "active|inactive", message = "status must be 'active' or 'inactive'")
    private String status;


    @Min(value = 0, message = "Age must be greater than 1")
    @Max(value = 150, message = "The age cannot exceed 150 years")
    @NotNull(message = "Age cannot be null")
    private Integer age;
}

