package banquemisr.challenge05.task.management.service.exception;

import banquemisr.challenge05.task.management.service.dto.GeneralResponseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;


@EqualsAndHashCode(callSuper = true)
@Data
public class ErrorResponse extends GeneralResponseDTO {

    private ErrorDetails errorDetails = new ErrorDetails();

    public ErrorResponse(HttpStatus status, int statusCode, String error, String message, String details) {
        this.setStatus(status);
        this.getErrorDetails().setError(error);
        this.setMessage(message);
        this.setStatusCode(statusCode);
        this.getErrorDetails().setDetails(details);
    }
}
