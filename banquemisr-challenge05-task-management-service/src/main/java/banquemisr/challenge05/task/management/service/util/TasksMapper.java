package banquemisr.challenge05.task.management.service.util;

import banquemisr.challenge05.task.management.service.dto.*;
import banquemisr.challenge05.task.management.service.model.Task;
import banquemisr.challenge05.task.management.service.model.TaskHistory;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TasksMapper {

    private final ModelMapper modelMapper;

    public TasksMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public TaskResponseDTO mapTaskModelToTasksResponseDTO(Task task) {
        TaskResponseDTO tasksResponseDTO = new TaskResponseDTO();
        TaskDTO taskDTO = modelMapper.map(task, TaskDTO.class);
        tasksResponseDTO.setTask(taskDTO);
        return tasksResponseDTO;
    }

    public Task mapTasksRequestDTOToTaskModel(TaskRequestDTO tasksRequestDTO) {
        return modelMapper.map(tasksRequestDTO, Task.class);
    }

    public Task mapTasksRequestDTOToTaskModel(TaskRequestDTO tasksRequestDTO, Task task) {
        task.setTitle(tasksRequestDTO.getTitle());
        task.setDescription(tasksRequestDTO.getDescription());
        task.setStatus(tasksRequestDTO.getStatus());
        task.setPriority(tasksRequestDTO.getPriority());
        task.setDueDate(tasksRequestDTO.getDueDate());
        return task;
    }

    public TaskHistoryDTO mapTaskHistoryModelToTaskHistoryResponseDTO(TaskHistory taskHistory) {
        TaskHistoryDTO taskHistoryDTO = new TaskHistoryDTO();
        taskHistoryDTO.setOperationType(taskHistory.getOperation().name());
        LoginUserDTO loginUserDTO = modelMapper.map(taskHistory.getCreatedBy(), LoginUserDTO.class);
        taskHistoryDTO.setCreatedBy(loginUserDTO);

        TaskDTO taskDTO = modelMapper.map(taskHistory.getTask(), TaskDTO.class);

        taskHistoryDTO.setTask(taskDTO);
        taskHistoryDTO.setCreateAt(taskHistory.getCreatedAt());
        return taskHistoryDTO;
    }
}
