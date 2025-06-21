package com.mitocode.product_service.exception;

import java.util.HashMap;
import java.util.Map;

public abstract class BusinessException extends RuntimeException {
    private final String errorCode;
    private final Map<String, Object> properties;

    protected BusinessException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
        this.properties = new HashMap<>();
    }

    public String getErrorCode() { return errorCode; }
    public Map<String, Object> getProperties() { return properties; }
    public void addProperty(String key, Object value) { properties.put(key, value); }
}