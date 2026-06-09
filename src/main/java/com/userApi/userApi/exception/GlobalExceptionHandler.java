package com.userApi.userApi.exception;


import com.userApi.userApi.dto.ErrorResponseDTO;
import com.userApi.userApi.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // Handle specific exception

    //User not found Exception
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleUserNotFound(UserNotFoundException ex) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ApiResponse<>(false, ex.getMessage(), null)
        );
    }

    // Category not found exception
    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleCategoryNotFound( CategoryNotFoundException ex){
       return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
        new ApiResponse<>(false, ex.getMessage(),null)
       );

    }

    //
//    Keycloak Role not found exception
    @ExceptionHandler(KeycloakUserCreationException.class)
    public ResponseEntity<ApiResponse<Void>> handleUserCreationError(KeycloakUserCreationException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                new ApiResponse<>(false, ex.getMessage(), null)
        );
    }

    @ExceptionHandler(InvalidRoleException.class)
    public ResponseEntity<ApiResponse<Void>>handleInvalidRoleException(InvalidRoleException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ApiResponse<>(false, ex.getMessage(), null)
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidationErrors(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ApiResponse<>(false, "Validation failed", errors)
        );
    }

    @ExceptionHandler(InvalidLoginRequestException.class)
    public ResponseEntity<ApiResponse<Object>> handleInvalidLoginRequest(
            InvalidLoginRequestException ex,
            HttpServletRequest request) {

        log.warn("Invalid login request mapping triggered: {}", ex.getMessage());

        ErrorResponseDTO errorBody = new ErrorResponseDTO(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );

        ApiResponse<Object> response = new ApiResponse<>(
                false,
                "Login request validation failed",
                errorBody
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse<Object>> handleBadCredentials(
            BadCredentialsException ex,
            HttpServletRequest request) {

        log.warn("Authentication failed: {}", ex.getMessage());

        ErrorResponseDTO errorBody = new ErrorResponseDTO(
                LocalDateTime.now(),
                HttpStatus.UNAUTHORIZED.value(),
                HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );

        ApiResponse<Object> response = new ApiResponse<>(
                false,
                "Unauthorized access",
                errorBody
        );

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(KeycloakAuthenticationException.class)
    public ResponseEntity<ApiResponse<Object>> handleKeycloakAuthentication(
            KeycloakAuthenticationException ex,
            HttpServletRequest request) {

        log.error("Keycloak identity provider communication failure: {}", ex.getMessage());

        ErrorResponseDTO errorBody = new ErrorResponseDTO(
                LocalDateTime.now(),
                HttpStatus.BAD_GATEWAY.value(),
                HttpStatus.BAD_GATEWAY.getReasonPhrase(),
                "The authentication service is temporarily unavailable. Please try again later.",
                request.getRequestURI()
        );

        ApiResponse<Object> response = new ApiResponse<>(
                false,
                "External Identity Provider Error",
                errorBody
        );

        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(response);
    }

    @ExceptionHandler(LoginServiceException.class)
    public ResponseEntity<ApiResponse<Object>> handleLoginServiceException(
            LoginServiceException ex,
            HttpServletRequest request) {

        log.error("Critical error inside the application login layer", ex);

        ErrorResponseDTO errorBody = new ErrorResponseDTO(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                "An internal error occurred while processing your authentication request.",
                request.getRequestURI()
        );

        ApiResponse<Object> response = new ApiResponse<>(
                false,
                "Internal Server Error",
                errorBody
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }



    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Void>> handleEmailAlreadyExists(EmailAlreadyExistsException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                new ApiResponse<>(false, ex.getMessage(), null)
        );
    }


//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ApiResponse<Void>> handleGeneral(Exception ex) {
//
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
//                new ApiResponse<>(false, "An unexpected error occurred", null)
//        );
//    }
}