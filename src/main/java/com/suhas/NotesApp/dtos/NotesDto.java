package com.suhas.NotesApp.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public record NotesDto(@NotBlank(message = "Title should not be blank") String title, String description,
                       LocalDateTime createdAt, UsersDto user) {
}
