package com.tms.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlogalExceptionHandler {

    @ExceptionHandler(value = AgeException.class)
    public String ageExceptionHandler(AgeException exception, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_CONFLICT);
        return exception.getMessage();
    }

    @ExceptionHandler(value = Exception.class)
    public String allExceptionsHandler(Exception exception, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_CONFLICT);
        return exception.getMessage();
    }
}
