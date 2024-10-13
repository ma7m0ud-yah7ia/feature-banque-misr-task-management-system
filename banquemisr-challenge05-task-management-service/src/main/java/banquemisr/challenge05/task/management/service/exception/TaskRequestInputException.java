package banquemisr.challenge05.task.management.service.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class TaskRequestInputException extends RuntimeException {
    private final List<String> errorMessages;
    public TaskRequestInputException(List<String> errorMessages) {
        super(String.join(",", errorMessages));
        this.errorMessages = errorMessages;
    }
}
