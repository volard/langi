package com.volard.langi.security.utils;

import reactor.core.publisher.Mono;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Validates TOKEN against requests coming from AuthenticationFilter ServerWebExchange.
 */
public class ServerHttpBearerToAuthenticationConverter {
    private static final String BEARER_PREFIX = "Bearer ";
    private static final Predicate<String> isMatchesBearerLength =
            authValue -> authValue.length() > BEARER_PREFIX.length();
    private static final Function<String, Mono<String>> getBearerValue =
            authValue -> Mono.justOrEmpty(authValue.substring(BEARER_PREFIX.length()));


}
