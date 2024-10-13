package banquemisr.challenge05.task.management.service.exception;

public class TaskResourceNotFoundException extends RuntimeException {

    public TaskResourceNotFoundException(String message) {
        super(message);
    }
}