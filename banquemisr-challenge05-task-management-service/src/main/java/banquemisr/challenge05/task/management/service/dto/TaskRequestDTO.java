package banquemisr.challenge05.task.management.service.dto;

import banquemisr.challenge05.task.management.service.enums.TaskPriority;
import banquemisr.challenge05.task.management.service.enums.TaskStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TaskRequestDTO {

    private String title;

    private String exposedId;

    private String description;

    private TaskStatus status;

    private TaskPriority priority;

    private Date dueDate;
}
