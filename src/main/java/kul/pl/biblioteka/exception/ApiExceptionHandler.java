package kul.pl.biblioteka.exception;

import com.sun.jdi.InternalException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityExistsException;
import javax.servlet.http.HttpServletRequest;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ResponseEntity<Object> handlerApiRequestException(HttpServletRequest req, IllegalArgumentException e){
        HttpStatus status = HttpStatus.BAD_REQUEST;

        ApiException exception = new ApiException(
                ZonedDateTime.now(ZoneId.of("Z")),
                status.value(),
                status.name(),
                e.getMessage(),
                req.getRequestURI()
        );
        return new ResponseEntity<>(exception, status);
    }

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    public ResponseEntity<Object> handlerResourceNotFoundException(HttpServletRequest req, ResourceNotFoundException e){

        HttpStatus status = HttpStatus.NOT_FOUND;
        ApiException exception = new ApiException(
                ZonedDateTime.now(ZoneId.of("Z")),
                status.value(),
                status.name(),
                e.getMessage(),
                req.getRequestURI()
        );
        return new ResponseEntity<>(exception, status);
    }

    @ExceptionHandler(value = {EntityExistsException.class})
    public ResponseEntity<Object> handlerEntityExistsException(HttpServletRequest req, EntityExistsException e){

        HttpStatus status = HttpStatus.CONFLICT;
        ApiException exception = new ApiException(
                ZonedDateTime.now(ZoneId.of("Z")),
                status.value(),
                status.name(),
                e.getMessage(),
                req.getRequestURI()
        );
        return new ResponseEntity<>(exception, status);
    }
}
