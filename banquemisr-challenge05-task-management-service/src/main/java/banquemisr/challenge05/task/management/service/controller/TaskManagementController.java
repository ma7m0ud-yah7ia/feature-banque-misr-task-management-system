package banquemisr.challenge05.task.management.service.controller;

import banquemisr.challenge05.task.management.service.dto.TaskRequestDTO;
import banquemisr.challenge05.task.management.service.dto.TaskResponseDTO;
import banquemisr.challenge05.task.management.service.dto.TasksHistorySearchResponseDTO;
import banquemisr.challenge05.task.management.service.dto.TasksSearchResponseDTO;
import banquemisr.challenge05.task.management.service.service.impl.TaskManagementServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


@Controller
@RequiredArgsConstructor
@Slf4j
public class TaskManagementController {

    private final TaskManagementServiceImpl taskManagementService;

    @GetMapping(value = "/get-tasks", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TasksSearchResponseDTO> getTasks(@RequestParam(name = "taskTitle", required = false) String taskTitle,
                                                           @RequestParam(name = "taskStatus", required = false) String taskStatus,
                                                           @RequestParam(name = "taskPriority", required = false) String taskPriority,
                                                           @RequestParam(name = "taskDesc", required = false) String taskDesc,
                                                           @RequestParam(name = "taskDueDateFrom", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)  Date taskDueDateFrom,
                                                           @RequestParam(name = "taskDueDateTo", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)  Date taskDueDateTo,
                                                           @RequestParam(name = "displayAll", required = false) boolean displayAll,
                                                           @RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "5") int sizePerPage,
                                                           @RequestHeader(name = "Accept-Language", defaultValue = "en") String lang) throws Exception {

        log.info("getTask request params: taskTitle = {}, taskStatus = {}, taskPriority:{}, taskDesc = {}," +
                        "taskDueDateFrom = {},taskDueDateTo = {}, page = {}, sizePerPage = {}", taskTitle, taskStatus, taskPriority, taskDesc, taskDueDateFrom,
                taskDueDateTo, page, sizePerPage);
        TasksSearchResponseDTO tasksResponseDTOs = taskManagementService.getTaskByCriteria(taskTitle, taskStatus, taskPriority, taskDesc,
                taskDueDateFrom, taskDueDateTo, displayAll,  page, sizePerPage);
        log.info("get tasks response: {}", tasksResponseDTOs);

        return new ResponseEntity<>(tasksResponseDTOs, HttpStatus.OK);
    }

    @GetMapping(value = "/get-tasks-history", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TasksHistorySearchResponseDTO> getTasksHistory(@RequestParam(name = "taskTitle", required = false) String taskTitle,
                                                                         @RequestParam(name = "taskDueDateFrom", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)  Date taskDueDateFrom,
                                                                         @RequestParam(name = "taskDueDateTo", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)  Date taskDueDateTo,
                                                                         @RequestParam(defaultValue = "0") int page,
                                                                         @RequestParam(defaultValue = "5") int sizePerPage,
                                                                         @RequestHeader(name = "Accept-Language", defaultValue = "en") String lang) throws Exception {

        log.info("getTask request params: taskExposedId = {}" +
                        "taskDueDateFrom = {},taskDueDateTo = {}, page = {}, sizePerPage = {}", taskTitle, taskDueDateFrom,
                taskDueDateTo, page, sizePerPage);
        TasksHistorySearchResponseDTO tasksHistorySearchResponseDTO = taskManagementService.getTaskHistoryByCriteria(taskTitle, taskDueDateFrom, taskDueDateTo, page, sizePerPage);
        log.info("get-tasks-history response: {}", tasksHistorySearchResponseDTO);

        return new ResponseEntity<>(tasksHistorySearchResponseDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/create-task", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskResponseDTO> createTask(@RequestHeader(name = "Accept-Language", defaultValue = "en") String lang,
                                                      @RequestBody TaskRequestDTO tasksRequestDTO) throws Exception {
        TaskResponseDTO response = taskManagementService.createTask(tasksRequestDTO, lang);
        log.info("task creation status: {}", response.getMessage());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/update-task")
    public ResponseEntity<TaskResponseDTO> updateTask(@RequestHeader(name = "Accept-Language", defaultValue = "en") String lang,
                                                      @RequestBody TaskRequestDTO tasksRequestDTO) throws Exception {

        TaskResponseDTO response = taskManagementService.updateTask(tasksRequestDTO, lang);
        log.info("task update status: {}", response.getMessage());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete-task/{exposedId}")
    public ResponseEntity<TaskResponseDTO> deleteTask(@RequestHeader(name = "Accept-Language", defaultValue = "en") String lang,
                                                      @PathVariable String exposedId) throws Exception {
        TaskResponseDTO response = taskManagementService.deleteTask(exposedId, lang);
        log.info("task delete status: {}", response.getMessage());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
