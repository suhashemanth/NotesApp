package com.suhas.NotesApp.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.suhas.NotesApp.enums.UserRoles;
import com.suhas.NotesApp.model.Notes;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public record UsersDto(@NotBlank(message = "Username should not be blank") String name,
                       @Email(message = "The emailId should contain @") String email,
                       @NotBlank(message = "Password cannot be blank") String password, UserRoles userRole,
                       LocalDateTime createdAt, LocalDateTime updatedAt, List<Notes> notes) {

}
