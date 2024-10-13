package banquemisr.challenge05.task.management.service.exception.handler;

import banquemisr.challenge05.task.management.service.exception.ErrorResponse;
import banquemisr.challenge05.task.management.service.exception.InvalidDataException;
import banquemisr.challenge05.task.management.service.exception.TaskRequestInputException;
import banquemisr.challenge05.task.management.service.exception.TaskResourceNotFoundException;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Arrays;
import java.util.List;


@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Resource(name = "messages_bundle")
    private final MessageSource messageSource;

    // Handle ResourceNotFoundException
    @ExceptionHandler(TaskResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(TaskResourceNotFoundException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND,
                HttpStatus.NOT_FOUND.value(),
                messageSource.getMessage(ex.getMessage(), null, request.getLocale()),
                Arrays.toString(ex.getStackTrace()));
        log.error("ResourceNotFoundException: {} ", errorResponse.getError().getErrorDetails());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    // Handle generic exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                ex.getMessage());
        log.error("GlobalException: {} ", errorResponse.getError().getErrorDetails());

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<ErrorResponse> handleInvalidDataException(InvalidDataException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                HttpStatus.BAD_REQUEST.value(),
                messageSource.getMessage("message.error.constraint.violation", null, request.getLocale()),
                messageSource.getMessage(ex.getMessage(), null, request.getLocale()));
        log.error("RuntimeException: {} ", errorResponse.getError().getErrorDetails());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TaskRequestInputException.class)
    public ResponseEntity<ErrorResponse> handleTaskRequestInputException(TaskRequestInputException ex, WebRequest request) {

        List<String> errorMessages = List.of(ex.getMessage().split(","));
        List<String> localizedMessages = new java.util.ArrayList<>(List.of());

        errorMessages.forEach(errorMessage -> {
            localizedMessages.add(messageSource.getMessage(errorMessage, null, request.getLocale()));
        });

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                HttpStatus.BAD_REQUEST.value(),
                messageSource.getMessage("message.error.invalid.input.parameters", null, request.getLocale()),
                "Validation failed: " + String.join(", ", localizedMessages));

        log.error("TaskRequestInputException: {} ", errorResponse.getError().getErrorDetails());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.valueOf(status.value()),
                status.value(),
                messageSource.getMessage("message.error.constraint.violation", null, request.getLocale()),
                messageSource.getMessage("message.error.constraint.datatype.violation", null, request.getLocale()));

        log.error("HttpMessageNotReadableException: {} ", errorResponse.getError().getErrorDetails());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
