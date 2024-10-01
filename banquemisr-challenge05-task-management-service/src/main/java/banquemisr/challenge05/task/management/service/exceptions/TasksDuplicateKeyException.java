package banquemisr.challenge05.task.management.service.exceptions;

import org.springframework.dao.DataIntegrityViolationException;

public class TasksDuplicateKeyException extends DataIntegrityViolationException {

    public TasksDuplicateKeyException(String message) {
        super(message);
    }
}
