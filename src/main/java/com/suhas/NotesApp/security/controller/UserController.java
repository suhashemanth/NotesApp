package com.suhas.NotesApp.security.controller;

import com.suhas.NotesApp.dtos.ApiResponse;
import com.suhas.NotesApp.dtos.UsersDto;
import com.suhas.NotesApp.enums.UserRoles;
import com.suhas.NotesApp.model.Notes;
import com.suhas.NotesApp.model.Users;
import com.suhas.NotesApp.security.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/auth/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<UsersDto>>> getAllUsers() {
        List<UsersDto> dtos = userService.getUsers().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        ApiResponse<List<UsersDto>> resp = ApiResponse.<List<UsersDto>>builder()
                .statusCode((long) HttpStatus.OK.value())
                .message("Fetched all users")
                .val(dtos)
                .build();
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/roles/{role}")
    public ResponseEntity<ApiResponse<List<UsersDto>>> getUsersByRole(@PathVariable UserRoles role) {
        List<UsersDto> dtos = userService.getUsersWithRoles(role).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        ApiResponse<List<UsersDto>> resp = ApiResponse.<List<UsersDto>>builder()
                .statusCode((long)HttpStatus.OK.value())
                .message("Fetched users with role: " + role)
                .val(dtos)
                .build();
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/{email}")
    public ResponseEntity<ApiResponse<UsersDto>> getUser(@PathVariable String email) {
        Users user = userService.getAUser(email);
        ApiResponse<UsersDto> resp = ApiResponse.<UsersDto>builder()
                .statusCode((long)HttpStatus.OK.value())
                .message("Fetched user: " + email)
                .val(toDto(user))
                .build();
        return ResponseEntity.ok(resp);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UsersDto>> addUser(
            @Valid @RequestBody UsersDto usersDto) {

        Users created = userService.AddAUser(toEntity(usersDto));
        ApiResponse<UsersDto> resp = ApiResponse.<UsersDto>builder()
                .statusCode((long)HttpStatus.CREATED.value())
                .message("User created successfully")
                .val(toDto(created))
                .build();
        return new ResponseEntity<>(resp, HttpStatus.CREATED);
    }

    @PutMapping("/{email}")
    public ResponseEntity<ApiResponse<UsersDto>> updateUser(
            @PathVariable String email,
            @Valid @RequestBody UsersDto usersDto) {

        Users updated = userService.updateAUser(email, toEntity(usersDto));
        ApiResponse<UsersDto> resp = ApiResponse.<UsersDto>builder()
                .statusCode((long)HttpStatus.OK.value())
                .message("User updated successfully")
                .val(toDto(updated))
                .build();
        return ResponseEntity.ok(resp);
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable String email) {
        userService.deleteAUser(email);
        ApiResponse<Void> resp = ApiResponse.<Void>builder()
                .statusCode((long)HttpStatus.OK.value())
                .message("User deleted successfully")
                .build();
        return ResponseEntity.ok(resp);
    }

    // ——— MAPPING HELPERS ———

    private UsersDto toDto(Users user) {

        return new UsersDto(
                user.getName(),
                user.getEmail(),
                null,               // never expose password
                user.getUserRole(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.getNotes()
        );
    }

    private Users toEntity(UsersDto dto) {
        Users user = new Users();
        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setPassword(dto.password());
        user.setUserRole(dto.userRole());
        return user;
    }
}
