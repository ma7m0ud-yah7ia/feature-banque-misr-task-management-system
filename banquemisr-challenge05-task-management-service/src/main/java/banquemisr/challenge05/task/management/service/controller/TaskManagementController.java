package banquemisr.challenge05.task.management.service.controller;

import banquemisr.challenge05.task.management.service.dto.TaskRequestDTO;
import banquemisr.challenge05.task.management.service.dto.TaskResponseDTO;
import banquemisr.challenge05.task.management.service.dto.TasksSearchResponseDTO;
import banquemisr.challenge05.task.management.service.exceptions.TasksDuplicateKeyException;
import banquemisr.challenge05.task.management.service.service.impl.TaskManagementServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequiredArgsConstructor
public class TaskManagementController {

    public static final Logger LOG = LoggerFactory.getLogger(TaskManagementController.class);
    private final TaskManagementServiceImpl taskManagementService;

    @GetMapping(value = "/get-tasks", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TasksSearchResponseDTO> getTask(@RequestParam(name = "taskTitle", required = false) String taskTitle,
                                                          @RequestParam(name = "taskStatus", required = false) String taskStatus,
                                                          @RequestParam(name = "taskDesc", required = false) String taskDesc,
                                                          @RequestParam(name = "taskDueDate", required = false) String taskDueDate,
                                                          @RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "5") int sizePerPage) throws TasksDuplicateKeyException {

        LOG.info("getTask request params: taskTitle = {}, taskStatus = {}, taskDesc = {}, " +
                "taskDueDate = {}, page = {}, sizePerPage = {}", taskTitle, taskStatus, taskDesc, taskDueDate, page, sizePerPage);
        TasksSearchResponseDTO tasksResponseDTOs = taskManagementService.getTaskByCriteria(taskTitle, taskStatus, taskDesc, taskDueDate, page, sizePerPage);
        LOG.info("getTask response: {}", tasksResponseDTOs);

        return new ResponseEntity<>(tasksResponseDTOs, HttpStatus.OK);
    }

    @PostMapping(value = "/create-task", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskResponseDTO> createTask(@RequestHeader(name = "Accept-Language") String lang,
                                                      @Valid @RequestBody TaskRequestDTO tasksRequestDTO) {
        TaskResponseDTO response = taskManagementService.createTask(tasksRequestDTO, lang);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/update-task")
    public ResponseEntity<TaskResponseDTO> updateTask(@Valid @RequestBody TaskRequestDTO tasksRequestDTO) throws TasksDuplicateKeyException {

        TaskResponseDTO response = taskManagementService.updateTask(tasksRequestDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete-task")
    public ResponseEntity<TaskResponseDTO> deleteTask(@RequestBody TaskRequestDTO tasksRequestDTO) throws TasksDuplicateKeyException {
        TaskResponseDTO response = taskManagementService.deleteTask(tasksRequestDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
