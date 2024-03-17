package zerobase.weather.config;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public Exception handleAllException() {
    System.out.println("error from GlobalException");
    return new Exception();

    }
}
