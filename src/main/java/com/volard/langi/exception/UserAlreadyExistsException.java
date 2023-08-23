package com.volard.langi.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.ErrorResponseException;
import java.net.URI;
import java.time.ZonedDateTime;

public class UserAlreadyExistsException extends ErrorResponseException {
    private final String detail = "The user you're trying to register is already exists";
    private final URI type = URI.create("http://localhost/docs/errors/user-already-exists");
    private final String title = "User already exists";

    private void init() {
        super.setType(type);
        super.setTitle(title);
        super.setDetail(detail);
        super.getHeaders().setDate(ZonedDateTime.now());
    }

    public UserAlreadyExistsException() {
        super(HttpStatusCode.valueOf(409));
        init();
    }

    public UserAlreadyExistsException(Throwable cause) {
        super(HttpStatusCode.valueOf(409), cause);
        init();
    }
}