package de.workshops.bookshelf;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler
    public ProblemDetail error(ConstraintViolationException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.I_AM_A_TEAPOT, ex.getMessage());
        problemDetail.setTitle("Your request input is invalid");
        problemDetail.setType(URI.create("http://localhost:8080/constraint_violation_exception.html"));
        problemDetail.setProperty("timestamp", Instant.now());

        return problemDetail;
    }
}
