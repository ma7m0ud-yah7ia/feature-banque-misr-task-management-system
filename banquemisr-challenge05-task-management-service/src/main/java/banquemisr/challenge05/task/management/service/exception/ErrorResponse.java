package banquemisr.challenge05.task.management.service.exception;

import banquemisr.challenge05.task.management.service.dto.GeneralResponseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;


@EqualsAndHashCode(callSuper = true)
@Data
public class ErrorResponse extends GeneralResponseDTO {

    private Error error = new Error();

    public ErrorResponse(HttpStatus status, int statusCode, String error, String details) {
        super(status, statusCode);
        this.getError().setErrorMessage(error);
        this.getError().setErrorDetails(details);
    }
}
