package banquemisr.challenge05.task.management.service.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@RequiredArgsConstructor
public class TaskResponseDTO extends GeneralResponseDTO {
    private TaskDTO task;
}
