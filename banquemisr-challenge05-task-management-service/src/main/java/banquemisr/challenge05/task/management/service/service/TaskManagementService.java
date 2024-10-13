package banquemisr.challenge05.task.management.service.service;

import banquemisr.challenge05.task.management.service.dto.TaskRequestDTO;
import banquemisr.challenge05.task.management.service.dto.TaskResponseDTO;
import banquemisr.challenge05.task.management.service.dto.TasksHistorySearchResponseDTO;
import banquemisr.challenge05.task.management.service.dto.TasksSearchResponseDTO;

import java.util.Date;


public interface TaskManagementService {
    TaskResponseDTO createTask(TaskRequestDTO tasksRequestDTO, String lang);

    TaskResponseDTO updateTask(TaskRequestDTO tasksRequestDTO, String lang) throws Exception;

    TasksSearchResponseDTO getTaskByCriteria(String taskTitle, String taskStatus, String taskPriority, String taskDesc,
                                             Date taskDueDateFrom, Date taskDueDateTo, boolean displayAll, int page, int sizePerPage);

    TaskResponseDTO deleteTask(String exposedId, String lang);

    TasksHistorySearchResponseDTO getTaskHistoryByCriteria(String taskExposedId, Date taskDueDateFrom, Date taskDueDateTo, int page, int sizePerPage);
}
