package com.mitocode.product_service.imp;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class ErrorProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate; // ← Cambiar a String
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String ERROR_TOPIC = "application-errors";
    private static final String SERVICE_NAME = "product-service";

    public CompletableFuture<Void> sendCustomErrorMessage(String endpoint,
                                                          String method,
                                                          int statusCode,
                                                          String errorMessage,
                                                          String additionalData) {
        try {
            // Crear mensaje como Map (será JSON)
            Map<String, Object> error = new HashMap<>();
            error.put("errorId", UUID.randomUUID().toString());
            error.put("timestamp", Instant.now().toEpochMilli());
            error.put("serviceName", SERVICE_NAME);
            error.put("httpMethod", method);
            error.put("endpoint", endpoint);
            error.put("statusCode", statusCode);
            error.put("errorMessage", errorMessage);
            error.put("additionalData", additionalData);

            // Convertir a JSON
            String jsonMessage = objectMapper.writeValueAsString(error);
            String messageId = (String) error.get("errorId");

            log.info("Sending error to Kafka as JSON: {}", messageId);

            // Función de envío de mensaje a través de Kafka Template
            // Nombre del tópico, id "generado por uuid" y mensaje "un json"
            return kafkaTemplate.send(ERROR_TOPIC, messageId, jsonMessage)
                    .thenAccept(result -> {
                        log.info("Error sent: {} - Partition: {}, Offset: {}",
                                messageId,
                                result.getRecordMetadata().partition(),
                                result.getRecordMetadata().offset());
                    })
                    .exceptionally(throwable -> {
                        log.error("Failed to send error: {}", messageId, throwable);
                        return null;
                    });
        } catch (Exception e) {
            log.error("Error building JSON message", e);
            return CompletableFuture.completedFuture(null);
        }
    }

    public CompletableFuture<Void> sendErrorMessage(Exception exception,
                                                    String httpMethod,
                                                    String endpoint,
                                                    int statusCode) {
        return sendCustomErrorMessage(
                endpoint,
                httpMethod,
                statusCode,
                exception.getMessage() != null ? exception.getMessage() : exception.getClass().getSimpleName(),
                null
        );
    }
}