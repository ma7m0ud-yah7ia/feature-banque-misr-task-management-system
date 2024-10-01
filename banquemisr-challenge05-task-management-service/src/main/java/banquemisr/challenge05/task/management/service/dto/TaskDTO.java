package banquemisr.challenge05.task.management.service.dto;

import banquemisr.challenge05.task.management.service.enums.TaskPriority;
import banquemisr.challenge05.task.management.service.enums.TaskStatus;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Data
@RequiredArgsConstructor
public class TaskDTO {

    private String title;
    private String description;
    private TaskStatus taskStatus;
    private TaskPriority priority;
    private Date dueDate;

}
