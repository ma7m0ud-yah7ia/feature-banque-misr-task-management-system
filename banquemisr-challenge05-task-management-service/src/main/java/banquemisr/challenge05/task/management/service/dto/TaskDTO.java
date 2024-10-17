package banquemisr.challenge05.task.management.service.dto;

import banquemisr.challenge05.task.management.service.enums.TaskPriority;
import banquemisr.challenge05.task.management.service.enums.TaskStatus;
import banquemisr.challenge05.task.management.service.model.Notification;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Data
@RequiredArgsConstructor
public class TaskDTO {

    private String exposedId;
    private String title;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;
    private Date dueDate;
    private Notification notification;

}
