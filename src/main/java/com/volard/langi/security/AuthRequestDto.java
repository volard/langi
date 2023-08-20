package com.volard.langi.security;

public record AuthRequestDto(
        String username,
        String password) {
}
