package com.suhas.NotesApp.controllerAdvice;

import com.suhas.NotesApp.dtos.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private static final Logger logger=Logger.getLogger(GlobalExceptionHandler.class.getName());
    @ExceptionHandler(Exception.class)
    ResponseEntity<ApiResponse> handleGenericExceptions(Exception ex)
    {
        logger.info("Generic Exception: "+ex.getMessage());
        ApiResponse badRequest = ApiResponse.builder().
                statusCode((long) HttpStatus.BAD_REQUEST.value()).message("Bad Request").build();
        return new ResponseEntity<>(badRequest,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex)
    {
        Map<String, List<String>> errors = ex.getFieldErrors().stream().collect(Collectors.
                groupingBy(FieldError::getField, Collectors.mapping(DefaultMessageSourceResolvable::getDefaultMessage,
                        Collectors.toList())));
        logger.info("Validation Exception"+errors);

        return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
    }
}
