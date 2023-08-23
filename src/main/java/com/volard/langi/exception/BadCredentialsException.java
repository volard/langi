package com.volard.langi.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.ErrorResponseException;

import java.net.URI;
import java.time.ZonedDateTime;

public class BadCredentialsException extends ErrorResponseException {
    private final String detail = "Bad credentials provided. " +
            "Please, checks correctness of username and password and try again";
    private final URI type = URI.create("http://localhost/docs/errors/bad-credentials");
    private final String title = "Bad credentials provided.";

    private void init() {
        super.setType(type);
        super.setTitle(title);
        super.setDetail(detail);
        super.getHeaders().setDate(ZonedDateTime.now());
    }

    public BadCredentialsException() {
        super(HttpStatusCode.valueOf(400)); //todo maybe its not 400?..
        init();
    }

    public BadCredentialsException(Throwable cause) {
        super(HttpStatusCode.valueOf(400), cause);
        init();
    }
}
