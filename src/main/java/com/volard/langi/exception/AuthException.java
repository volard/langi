package com.volard.langi.exception;

public class AuthException extends ErrorResponse {
    public AuthException(String message, String errorCode) {
        super(message, errorCode);
    }
}