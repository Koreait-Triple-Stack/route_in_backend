package triple_stack.route_in_backend.security.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import triple_stack.route_in_backend.dto.ApiRespDto;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException e) {
        return ResponseEntity.ok(new ApiRespDto<>("failed", "문제가 발생했습니다: " + e.getMessage(), null));
    }
}
