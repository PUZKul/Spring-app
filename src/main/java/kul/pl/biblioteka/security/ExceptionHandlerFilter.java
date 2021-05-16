package kul.pl.biblioteka.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
            ApiException errorResponse = new ApiException(
                    ZonedDateTime.now(ZoneId.of("Z")),
                    status.value(),
                    status.name(),
                    e.getMessage(),
                    request.getRequestURI()
            );

            response.setStatus(status.value());
            response.getWriter().write(convertObjectToJson(errorResponse));
        }
    }

    public String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) return null;
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }


}