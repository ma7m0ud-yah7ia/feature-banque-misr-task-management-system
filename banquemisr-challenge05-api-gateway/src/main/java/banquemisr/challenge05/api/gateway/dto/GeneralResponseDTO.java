package banquemisr.challenge05.api.gateway.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;


@Data
public class GeneralResponseDTO {
    private String timestamp;
    private HttpStatus status;
    private int statusCode;

    public GeneralResponseDTO(HttpStatus status, int statusCode) {
        setTimestamp(LocalDateTime.now().toString());
        setStatus(status);
        setStatusCode(statusCode);
    }
}
