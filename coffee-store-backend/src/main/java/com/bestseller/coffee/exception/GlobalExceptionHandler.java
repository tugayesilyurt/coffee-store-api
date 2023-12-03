package com.bestseller.coffee.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler({RuntimeException.class})
    public ProblemDetail handleRuntimeException(RuntimeException exception,
                                                final HttpServletRequest request) {
        log.error("RunTimeException : " + exception.getMessage()
        +" - Path : " + request.getServletPath());

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        problemDetail.setTitle("RuntimeException");
        return problemDetail;

    }

    @ExceptionHandler({DrinkAlreadyExistException.class})
    public ProblemDetail handleDrinkAlreadyExistException(RuntimeException exception,
                                                         final HttpServletRequest request) {
        log.error("DrinkAlreadyExistException : " + exception.getMessage()
                +" - Path : " + request.getServletPath());

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getMessage());
        problemDetail.setTitle("DrinkAlreadyExistException");
        return problemDetail;
    }

    @ExceptionHandler({DrinkNotFoundException.class})
    public ProblemDetail handleDrinkNotFoundException(RuntimeException exception,
                                                                   final HttpServletRequest request) {
        log.error("DrinkNotFoundException : " + exception.getMessage()
                +" - Path : " + request.getServletPath());

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getMessage());
        problemDetail.setTitle("DrinkNotFoundException");
        return problemDetail;
    }

    @ExceptionHandler({ToppingAlreadyExistException.class})
    public ProblemDetail handleToppingAlreadyExistException(RuntimeException exception,
                                                            final HttpServletRequest request) {
        log.error("ToppingAlreadyExistException : " + exception.getMessage()
                +" - Path : " + request.getServletPath());

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getMessage());
        problemDetail.setTitle("ToppingAlreadyExistException");
        return problemDetail;
    }

    @ExceptionHandler({ToppingNotFoundException.class})
    public ProblemDetail handleToppingNotFoundException(RuntimeException exception,
                                                        final HttpServletRequest request) {
        log.error("ToppingNotFoundException : " + exception.getMessage()
                +" - Path : " + request.getServletPath());

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getMessage());
        problemDetail.setTitle("ToppingNotFoundException");
        return problemDetail;
    }
}
