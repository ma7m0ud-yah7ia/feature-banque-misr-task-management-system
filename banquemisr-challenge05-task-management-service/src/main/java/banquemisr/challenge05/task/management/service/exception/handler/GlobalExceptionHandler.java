package banquemisr.challenge05.task.management.service.exception.handler;

import banquemisr.challenge05.task.management.service.exception.ErrorResponse;
import banquemisr.challenge05.task.management.service.exceptions.ResourceNotFoundException;
import jakarta.annotation.Resource;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Resource(name = "messages_bundle")
    private final MessageSource messageSource;

    // Handle ResourceNotFoundException
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND,
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    // Handle validation errors
    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                               HttpHeaders headers,
                                                               HttpStatusCode status,
                                                               WebRequest request) {
        Map<String, String> validationErrors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> validationErrors.put(error.getField(), error.getDefaultMessage()));

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                HttpStatus.BAD_REQUEST.value(),
                "Validation Failed",
                messageSource.getMessage("message.error.Invalid.input.parameters", null, request.getLocale()),
                validationErrors.toString()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }


    // Handle generic exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex, WebRequest request) {
            ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Handle specific validation constraint violations
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                HttpStatus.BAD_REQUEST.value(),
                messageSource.getMessage("message.error.constraint.violation", null, request.getLocale()),
                ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}
