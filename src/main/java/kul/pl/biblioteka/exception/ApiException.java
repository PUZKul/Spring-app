package kul.pl.biblioteka.exception;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public class ApiException {
    private final ZonedDateTime timestamp;
    private final int status;
    private final String error;
    private final String message;
    private final String path;

    public ApiException(ZonedDateTime timestamp, int httpStatus, String error, String message, String path) {
        this.timestamp = timestamp;
        this.status = httpStatus;
        this.error = error;
        this.message = message;
        this.path = path;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }
}
