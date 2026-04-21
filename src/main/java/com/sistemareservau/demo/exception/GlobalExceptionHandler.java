package com.sistemareservau.demo.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice; // Importante

import com.sistemareservau.demo.dto.response.ErrorResponse;

@RestControllerAdvice 
public class GlobalExceptionHandler {

    // 1. VALIDACIÓN (@Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errores.put(error.getField(), error.getDefaultMessage());
        });

        //auxiliar para mantener orden
        return buildErrorResponseWithDetails("Error de validación", "VALIDATION_FAILED", HttpStatus.BAD_REQUEST, errores);
    }

    // 2. RECURSO NO ENCONTRADO
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException ex) {
        return buildErrorResponse(ex.getMessage(), "NOT_FOUND", HttpStatus.NOT_FOUND);
    }

    // 3. REGLAS DE NEGOCIO
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusiness(BusinessException ex) {
        return buildErrorResponse(ex.getMessage(), "BUSINESS_RULE_ERROR", HttpStatus.BAD_REQUEST);
    }

    // 4. CONFLICTOS 
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponse> handleConflict(ConflictException ex) {
        return buildErrorResponse(ex.getMessage(), "CONFLICT_ERROR", HttpStatus.CONFLICT);
    }

    // 5. ERROR GENÉRICO 
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex) {
        return buildErrorResponse("Ocurrió un error inesperado", "INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // AUXILIARES 
    private ResponseEntity<ErrorResponse> buildErrorResponse(String mensaje, String codigo, HttpStatus status) {
        return buildErrorResponseWithDetails(mensaje, codigo, status, null);
    }

    private ResponseEntity<ErrorResponse> buildErrorResponseWithDetails(String mensaje, String codigo, HttpStatus status, Map<String, String> detalles) {
        ErrorResponse error = ErrorResponse.builder()
                .codigo(codigo)
                .mensaje(mensaje)
                .timestamp(LocalDateTime.now())
                .detalles(detalles)
                .build();
        return new ResponseEntity<>(error, status);
    }
}