package banquemisr.challenge05.task.management.service.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class GeneralResponseDTO {
    private LocalDateTime timestamp;
    private HttpStatus status = HttpStatus.OK;
    private int statusCode = HttpStatus.OK.value();
    private String message;

    public GeneralResponseDTO() {
        this.timestamp = LocalDateTime.now();
    }

    public GeneralResponseDTO(HttpStatus status, String message, int statusCode) {
        setTimestamp(LocalDateTime.now());
        setStatus(status);
        setStatusCode(statusCode);
        setMessage(message);
    }

}
