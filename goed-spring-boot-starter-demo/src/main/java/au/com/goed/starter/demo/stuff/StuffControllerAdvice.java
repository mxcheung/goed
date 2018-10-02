package au.com.goed.starter.demo.stuff;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(HIGHEST_PRECEDENCE)
public class StuffControllerAdvice {

    @ExceptionHandler(Throwable.class)
    ResponseEntity<String> handleException(Throwable exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exception.getMessage());
    }
    
    @ExceptionHandler(StuffException.class)
    ResponseEntity<String> handleException(StuffException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }
}
