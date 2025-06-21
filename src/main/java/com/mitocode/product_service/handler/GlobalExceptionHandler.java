package com.mitocode.product_service.handler;

import com.mitocode.product_service.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.time.Instant;
import java.util.List;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ProblemDetail> handleBusinessException(BusinessException ex) {
        HttpStatus status = getHttpStatus(ex);

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, ex.getMessage());
        problemDetail.setTitle(getTitle(ex));
        problemDetail.setProperty("errorCode", ex.getErrorCode());
        problemDetail.setProperty("timestamp", Instant.now());

        // Agregar propiedades específicas de la excepción
        ex.getProperties().forEach(problemDetail::setProperty);

        return ResponseEntity.status(status).body(problemDetail);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleValidationException(MethodArgumentNotValidException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST, "Error de validación en los datos enviados");

        problemDetail.setTitle("Validation Error");
        problemDetail.setProperty("errorCode", "FIELD_VALIDATION_ERROR");
        problemDetail.setProperty("timestamp", Instant.now());

        // Agregar errores de campo específicos
        List<Map<String, String>> fieldErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> Map.of(
                        "field", error.getField(),
                        "message", error.getDefaultMessage(),
                        "rejectedValue", String.valueOf(error.getRejectedValue())
                ))
                .toList();

        problemDetail.setProperty("fieldErrors", fieldErrors);
        return ResponseEntity.badRequest().body(problemDetail);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleGenericException(Exception ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.INTERNAL_SERVER_ERROR, "Error interno del servidor");

        log.debug("REVISANDO EL ERROR {}", ex.getMessage());
        log.debug("REVISANDO EL stack {}", (Object) ex.getStackTrace());

        problemDetail.setTitle("Internal Server Error");
        problemDetail.setProperty("errorCode", "INTERNAL_ERROR");
        problemDetail.setProperty("timestamp", Instant.now());

        // En producción, no exponer detalles internos
       // if (isDevEnvironment()) {
            problemDetail.setProperty("exception", ex.getClass().getSimpleName());
            problemDetail.setProperty("stackTrace", ex.getMessage());
       // }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(problemDetail);
    }

    private HttpStatus getHttpStatus(BusinessException ex) {
        ResponseStatus annotation = ex.getClass().getAnnotation(ResponseStatus.class);
        return annotation != null ? annotation.value() : HttpStatus.BAD_REQUEST;
    }

    private String getTitle(BusinessException ex) {
        return switch (ex.getErrorCode()) {
            case "VALIDATION_ERROR" -> "Error de Validación";
            case "RESOURCE_NOT_FOUND" -> "Recurso No Encontrado";
            case "BUSINESS_RULE_VIOLATION" -> "Violación de Regla de Negocio";
            default -> "Error de Negocio";
        };
    }

    private boolean isDevEnvironment() {
        // Implementar lógica para detectar ambiente de desarrollo
        return "local".equals(System.getProperty("spring.profiles.active"));
    }
}
