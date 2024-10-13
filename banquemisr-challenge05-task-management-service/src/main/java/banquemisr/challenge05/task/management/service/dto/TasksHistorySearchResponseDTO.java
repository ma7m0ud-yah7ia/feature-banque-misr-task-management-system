package banquemisr.challenge05.task.management.service.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class TasksHistorySearchResponseDTO extends GeneralPageableDTO {
    private List<TaskHistoryDTO> tasksHistory = new ArrayList<>();
}