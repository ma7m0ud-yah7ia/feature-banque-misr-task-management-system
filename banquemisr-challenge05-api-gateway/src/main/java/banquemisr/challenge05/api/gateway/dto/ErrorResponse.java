package banquemisr.challenge05.api.gateway.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;


@EqualsAndHashCode(callSuper = true)
@Setter
@Getter
public class ErrorResponse extends GeneralResponseDTO {

    private Error error = new Error();

    public ErrorResponse(HttpStatus status, int statusCode, String error, String details) {
        super(status,statusCode);
        this.getError().setErrorMessage(error);
        this.getError().setErrorDetails(details);
    }

}
