package com.volard.langi.security.utils;

// todo maybe LoginRequest? Maybe burn it down?
public record AuthRequestDto(
        String username,
        String password,
        String email) {
}
