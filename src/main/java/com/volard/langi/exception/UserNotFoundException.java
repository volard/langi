package com.volard.langi.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.ErrorResponseException;
import java.net.URI;
import java.time.ZonedDateTime;

public class UserNotFoundException extends ErrorResponseException {
    private final String detail = "The user you're trying to find not found";
    private final URI type = URI.create("http://localhost/docs/errors/user-not-found");
    private final String title = "User not found";

    private void init() {
        super.setType(type);
        super.setTitle(title);
        super.setDetail(detail);
        super.getHeaders().setDate(ZonedDateTime.now());
    }

    public UserNotFoundException() {
        super(HttpStatusCode.valueOf(409));
        init();
    }

    public UserNotFoundException(Throwable cause) {
        super(HttpStatusCode.valueOf(409), cause);
        init();
    }
}