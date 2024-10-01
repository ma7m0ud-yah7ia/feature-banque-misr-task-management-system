package banquemisr.challenge05.task.management.service.service;

import banquemisr.challenge05.task.management.service.dto.TaskRequestDTO;
import banquemisr.challenge05.task.management.service.dto.TaskResponseDTO;
import banquemisr.challenge05.task.management.service.dto.TasksSearchResponseDTO;


public interface TaskManagementService {
    TaskResponseDTO createTask(TaskRequestDTO tasksRequestDTO, String lang);

    TaskResponseDTO updateTask(TaskRequestDTO tasksRequestDTO);

    TasksSearchResponseDTO getTaskByCriteria(String taskTitle, String taskStatus, String taskDesc, String taskDueDate, int page, int sizePerPage);

    TaskResponseDTO deleteTask(TaskRequestDTO tasksRequestDTO);

}
