package kul.pl.biblioteka.exception;

import com.sun.jdi.InternalException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ResponseEntity<Object> handlerApiRequestException(HttpServletRequest req, IllegalArgumentException e){
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        ApiException exception = new ApiException(
                ZonedDateTime.now(ZoneId.of("Z")),
                badRequest.value(),
                badRequest.name(),
                e.getMessage(),
                req.getRequestURI()
        );
        return new ResponseEntity<>(exception, badRequest);
    }


}
