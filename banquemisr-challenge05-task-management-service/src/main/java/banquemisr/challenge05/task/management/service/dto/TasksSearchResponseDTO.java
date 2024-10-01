package banquemisr.challenge05.task.management.service.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@RequiredArgsConstructor
public class TasksSearchResponseDTO extends GeneralResponseDTO{
    private List<TaskDTO> tasks = new ArrayList<>();
    private int page;
    private int totalPages;
    private int totalSize;
}
