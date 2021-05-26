package kul.pl.biblioteka.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import javax.persistence.EntityExistsException;
import javax.servlet.http.HttpServletRequest;

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

    @ExceptionHandler(value = {AlreadyExtendedException.class})
    public ResponseEntity<Object> handlerAlreadyExtendedException(HttpServletRequest req, AlreadyExtendedException e){

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

    @ExceptionHandler(value = {AuthorisationException.class})
    public ResponseEntity<Object> handlerAuthorisationException(HttpServletRequest req, AuthorisationException e){

        HttpStatus status = HttpStatus.FORBIDDEN;
        ApiException exception = new ApiException(
                ZonedDateTime.now(ZoneId.of("Z")),
                status.value(),
                status.name(),
                e.getMessage(),
                req.getRequestURI()
        );
        return new ResponseEntity<>(exception, status);
    }

  @ExceptionHandler(value = {InvalidTokenException.class})
  public ResponseEntity<Object> handlerInvalidTokenException(HttpServletRequest req, InvalidTokenException e){

    HttpStatus status = HttpStatus.UNAUTHORIZED;
    ApiException exception = new ApiException(
        ZonedDateTime.now(ZoneId.of("Z")),
        status.value(),
        status.name(),
        e.getMessage(),
        req.getRequestURI()
    );
    return new ResponseEntity<>(exception, status);
  }

  @ExceptionHandler(value = {BookIsOccupiedException.class})
  public ResponseEntity<Object> handlerBookIsOccupiedException(HttpServletRequest req, BookIsOccupiedException e){

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

  @ExceptionHandler(value = {AlreadyBorrowedException.class})
  public ResponseEntity<Object> handlerAlreadyBorrowedException(HttpServletRequest req, AlreadyBorrowedException e){

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
