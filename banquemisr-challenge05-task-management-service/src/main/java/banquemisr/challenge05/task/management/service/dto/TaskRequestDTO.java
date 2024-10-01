package banquemisr.challenge05.task.management.service.dto;

import banquemisr.challenge05.task.management.service.enums.TaskPriority;
import banquemisr.challenge05.task.management.service.enums.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Data
@RequiredArgsConstructor
public class TaskRequestDTO {

    @NotBlank(message = "{message.error.title.input}")
    private String title;

    private String description;

    @NotNull(message = "{message.error.status.input}")
    private TaskStatus status;

    @NotNull(message = "{message.error.priority.input}")
    private TaskPriority priority;

    @NotNull(message = "{message.error.dueDate.input}")
    private Date dueDate;
}
