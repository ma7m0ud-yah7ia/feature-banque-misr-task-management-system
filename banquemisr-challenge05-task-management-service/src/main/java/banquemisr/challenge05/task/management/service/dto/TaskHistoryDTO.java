package banquemisr.challenge05.task.management.service.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
public class TaskHistoryDTO{
    private String operationType;
    private TaskDTO task;
    private LoginUserDTO createdBy;
    private Date createAt;
}
