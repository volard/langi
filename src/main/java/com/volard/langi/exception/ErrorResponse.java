package com.volard.langi.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponseException;

import java.util.Date;

public class ErrorResponse extends ErrorResponseException {

    @Getter
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private final Date timestamp = new Date();

    public ErrorResponse(HttpStatusCode status) {
        super(status);
    }

    public ErrorResponse(HttpStatusCode status, Throwable cause) {
        super(status, cause);
    }

    public ErrorResponse(HttpStatusCode status, ProblemDetail body, Throwable cause) {
        super(status, body, cause);
    }

    public ErrorResponse(HttpStatusCode status, ProblemDetail body, Throwable cause, String messageDetailCode, Object[] messageDetailArguments) {
        super(status, body, cause, messageDetailCode, messageDetailArguments);
    }

    @Override
    public String getLocalizedMessage(){
        // todo implement
        return "Bro, it's not implemented yet. Keep working, ok?";
    }

}