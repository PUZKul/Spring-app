package kul.pl.biblioteka.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import kul.pl.biblioteka.exception.ApiException;
import kul.pl.biblioteka.exception.InvalidTokenException;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class ExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (InvalidTokenException e) {
            HttpStatus status = HttpStatus.UNAUTHORIZED;
            ApiException errorResponse = createExceptionBody(status, e.getMessage(), request.getRequestURI());

            response.setStatus(status.value());
            response.getWriter().write(convertObjectToJson(errorResponse));
            response.addHeader("Content-Type", "application/json");
        }
        catch (IllegalArgumentException e){
          HttpStatus status = HttpStatus.BAD_REQUEST;
          ApiException errorResponse = createExceptionBody(status, e.getMessage(), request.getRequestURI());

          response.setStatus(status.value());
          response.getWriter().write(convertObjectToJson(errorResponse));
            response.addHeader("Content-Type", "application/json");
        }
        catch (Exception e){
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            ApiException errorResponse = createExceptionBody(status, e.getMessage(), request.getRequestURI());

            response.setStatus(status.value());
            response.getWriter().write(convertObjectToJson(errorResponse));
            response.addHeader("Content-Type", "application/json");
        }
    }

  private ApiException createExceptionBody(HttpStatus status, String message, String uri) {
    return new ApiException(
        ZonedDateTime.now(ZoneId.of("Z")),
        status.value(),
        status.name(),
        message,
        uri
    );
  }

  private String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) return null;
        ObjectWriter writer = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
                .writer().withDefaultPrettyPrinter();
        return writer.writeValueAsString(object);
    }
}