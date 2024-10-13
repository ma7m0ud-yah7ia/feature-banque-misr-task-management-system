package banquemisr.challenge05.task.management.service.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class GeneralResponseDTO {
    private String timestamp = LocalDateTime.now().toString();
    private HttpStatus status = HttpStatus.OK;
    private int statusCode = HttpStatus.OK.value();

    public GeneralResponseDTO() {
    }

    public GeneralResponseDTO(HttpStatus status, int statusCode) {
        setStatus(status);
        setStatusCode(statusCode);
    }
}
