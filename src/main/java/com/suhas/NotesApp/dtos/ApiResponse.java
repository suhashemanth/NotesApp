package com.suhas.NotesApp.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Value;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Value
public class ApiResponse<T> {
     Long statusCode;
     String message;
     @Builder.Default
     Map<String, List<String>> errors=new HashMap<>();
     String token;
     String role;
     String expirationDate;
     List<T> data;
     T val;
}
