package banquemisr.challenge05.task.management.service.service.impl;

import banquemisr.challenge05.task.management.service.dao.impl.TaskDaoImpl;
import banquemisr.challenge05.task.management.service.dto.*;
import banquemisr.challenge05.task.management.service.enums.Roles;
import banquemisr.challenge05.task.management.service.exception.InvalidDataException;
import banquemisr.challenge05.task.management.service.exception.TaskRequestInputException;
import banquemisr.challenge05.task.management.service.model.LoginUser;
import banquemisr.challenge05.task.management.service.model.Task;
import banquemisr.challenge05.task.management.service.model.TaskHistory;
import banquemisr.challenge05.task.management.service.service.TaskManagementService;
import banquemisr.challenge05.task.management.service.util.StringDateConverters;
import banquemisr.challenge05.task.management.service.util.TasksMapper;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static banquemisr.challenge05.task.management.service.enums.DateFormats.GENERAL_DATE_TIME_FORMAT;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskManagementServiceImpl implements TaskManagementService {

    private final TasksMapper tasksMapper;
    private final UserServiceImpl userService;

    @Resource(name = "messages_bundle")
    private final MessageSource messageSource;

    private final KafkaTemplate<String, TaskEventDTO> kafkaTemplate;

    private final TaskDaoImpl taskDao;

    @Value("${spring.kafka.template.default-topic}")
    private String kafkaDefaultTopic;

    @Caching(evict = {@CacheEvict(value = {"tasks"}, key = "customKeyGenerator", allEntries = true),
            @CacheEvict(value = {"taskHistory"}, key = "customKeyGenerator", allEntries = true)})
    public TaskResponseDTO createTask(TaskRequestDTO taskRequestDTO, String lang) {

        validateCreateTaskRequestParams(taskRequestDTO);

        Task taskObj = prepareTaskForSave(taskRequestDTO, userService.getLoginUser());
        Task savedTask = taskDao.saveTask(taskObj, lang);

        log.info("Task with title: {}, saved successfully with exposedId: {}", savedTask.getTitle(), savedTask.getExposedId());
        TaskResponseDTO tasksResponseDTO = tasksMapper.mapTaskModelToTasksResponseDTO(savedTask);

        tasksResponseDTO.setMessage(messageSource.getMessage("message.task.created.successfully", null, new Locale(lang)));

        return tasksResponseDTO;
    }

    @Caching(evict = {@CacheEvict(value = {"tasks"}, key = "customKeyGenerator", allEntries = true),
            @CacheEvict(value = {"taskHistory"}, key = "customKeyGenerator", allEntries = true)})
    public TaskResponseDTO updateTask(TaskRequestDTO taskRequestDTO, String lang) throws Exception {

        validateUpdateTaskRequestParams(taskRequestDTO);

        Task taskObj = prepareTaskForUpdate(taskRequestDTO, userService.getLoginUser());

        Map<String, String> updateFlags = checkUpdateFlags(taskObj, taskRequestDTO);

        TaskResponseDTO tasksResponseDTO = tasksMapper.mapTaskModelToTasksResponseDTO(
                taskDao.updateTask(tasksMapper.mapTasksRequestDTOToTaskModel(taskRequestDTO, taskObj), userService.getLoginUser(), lang));

        // if the update occurred by the admin, email the task creator
        if (!updateFlags.isEmpty()) {
            sendEmail(taskObj, tasksResponseDTO, updateFlags);
            log.info("Task of exposedId: {}, sent updates to email-service successfully", taskObj.getExposedId());
        }

        tasksResponseDTO.setMessage(messageSource.getMessage("message.task.updated.successfully", null, new Locale(lang)));
        return tasksResponseDTO;
    }


    @Caching(evict = {@CacheEvict(value = {"tasks"}, key = "customKeyGenerator", allEntries = true),
            @CacheEvict(value = {"taskHistory"}, key = "customKeyGenerator", allEntries = true)})
    public TaskResponseDTO deleteTask(String exposedId, String lang) {

        // Admin can't delete the task.
        Optional<Task> taskChecker = taskDao.findByExposedIdAndUser(exposedId, userService.getLoginUser());

        if (taskChecker.isEmpty()) {
            throw new InvalidDataException("message.error.task.not.found");
        }

        TaskResponseDTO tasksResponseDTO = tasksMapper.mapTaskModelToTasksResponseDTO(taskDao.deletTask(taskChecker.get(), userService.getLoginUser(), lang));

        log.info("Task of exposedId: {}, deleted successfully", exposedId);
        tasksResponseDTO.setMessage(messageSource.getMessage("message.task.deleted.successfully", null, new Locale(lang)));
        return tasksResponseDTO;

    }

    @Cacheable(value = "tasks", keyGenerator = "customKeyGenerator")
    @Override
    public TasksSearchResponseDTO getTaskByCriteria(String taskTitle, String taskStatus, String taskPriority, String taskDesc,
                                                    Date taskDueDateFrom, Date taskDueDateTo, boolean displayAll, int page, int sizePerPage) {
        int pageNumber = page - 1;

        Page<Task> tasksPage = taskDao.findByTitleDescStatusPriorityDueDate(
                taskTitle,
                taskStatus,
                taskPriority,
                taskDesc,
                taskDueDateFrom,
                taskDueDateTo,
                checkUserIfAdminOrNot(userService.getLoginUser()), displayAll, userService.getLoginUser(),
                PageRequest.of(pageNumber, sizePerPage, Sort.by("dueDate").descending()));

        TasksSearchResponseDTO tasksSearchResponseDTO = new TasksSearchResponseDTO();

        tasksPage.getContent().forEach(task -> {
            tasksSearchResponseDTO.getTasks().add(tasksMapper.mapTaskModelToTasksResponseDTO(task).getTask());
        });

        tasksSearchResponseDTO.setPage(page);
        tasksSearchResponseDTO.setTotalPages(tasksPage.getTotalPages());
        tasksSearchResponseDTO.setTotalSize((int) tasksPage.getTotalElements());

        return tasksSearchResponseDTO;
    }

    @Cacheable(value = "taskHistory", keyGenerator = "customKeyGenerator")
    @Override
    public TasksHistorySearchResponseDTO getTaskHistoryByCriteria(String taskTitle, Date taskDueDateFrom, Date taskDueDateTo, int page, int sizePerPage) {

        int pageNumber = page - 1;
        Page<TaskHistory> tasksPage = taskDao.findByTaskTitleAndDueDate(
                taskTitle,
                taskDueDateFrom, taskDueDateTo, PageRequest.of(pageNumber, sizePerPage, Sort.by("dueDate").descending()));
        TasksHistorySearchResponseDTO tasksHistorySearchResponseDTO = new TasksHistorySearchResponseDTO();
        tasksPage.getContent().forEach(taskHistory -> {
            tasksHistorySearchResponseDTO.getTasksHistory().add(tasksMapper.mapTaskHistoryModelToTaskHistoryResponseDTO(taskHistory));
        });

        tasksHistorySearchResponseDTO.setPage(page);
        tasksHistorySearchResponseDTO.setTotalPages(tasksPage.getTotalPages());
        tasksHistorySearchResponseDTO.setTotalSize((int) tasksPage.getTotalElements());

        return tasksHistorySearchResponseDTO;

    }

    private Task prepareTaskForUpdate(TaskRequestDTO taskRequestDTO, LoginUser user) {

        if (taskRequestDTO.getExposedId() == null || taskRequestDTO.getExposedId().isEmpty() || taskRequestDTO.getExposedId().isBlank()) {
            throw new InvalidDataException("message.error.exposedId.input");
        }
        // Check if the user is admin or not
        Task task = getTaskByUserPrivilege(checkUserIfAdminOrNot(userService.getLoginUser()), userService.getLoginUser(), taskRequestDTO.getExposedId());

        if (task == null) {
            throw new InvalidDataException("message.error.task.update.validation");
        }

        task.setUpdatedAt(new Date());
        task.setUpdatedBy(user);

        return task;
    }

    private Task prepareTaskForSave(TaskRequestDTO taskRequestDTO, LoginUser user) {
        Task taskObj = Objects.requireNonNull(tasksMapper.mapTasksRequestDTOToTaskModel(taskRequestDTO));
        taskObj.setExposedId(UUID.randomUUID().toString());
        taskObj.setCreatedBy(user);
        taskObj.setCreatedAt(new Date());

        return taskObj;
    }

    private boolean checkUserIfAdminOrNot(LoginUser user) {
        return !user.getGrantedAuthorities().stream()
                .filter(grantedAuth -> Roles.ADMIN.getValue().trim().equals(grantedAuth.trim())).collect(Collectors.toSet()).isEmpty();
    }

    private Task getTaskByUserPrivilege(boolean isAdmin, LoginUser user, String exposedId) {
        Optional<Task> taskChecker = Optional.of(new Task());
        if (isAdmin) {
            taskChecker = taskDao.findByExposedIdAndUser(exposedId, null);
        } else {
            taskChecker = taskDao.findByExposedIdAndUser(exposedId, user);
        }
        return taskChecker.orElse(null);
    }


    private void sendEmail(Task taskObj,
                           TaskResponseDTO tasksResponseDTO,
                           Map<String, String> updateFlags) {
        kafkaTemplate.send(kafkaDefaultTopic, new TaskEventDTO(
                taskObj.getCreatedBy().getEmail()
                , taskObj.getTitle()
                , tasksResponseDTO.getTask().getPriority().name()
                , tasksResponseDTO.getTask().getDueDate().toString()
                , taskObj.getCreatedBy().getUserFullName()
                , taskObj.getCreatedBy().getUsername()
                , updateFlags));
    }

    private Map<String, String> checkUpdateFlags(Task taskObj, TaskRequestDTO taskRequestDTO) {

        Map<String, String> updateFlags = new HashMap<>();

        if (!taskObj.getCreatedBy().equals(taskObj.getUpdatedBy())) {
            if (taskObj.getTitle() != null && !taskObj.getTitle().equals(taskRequestDTO.getTitle())) {
                updateFlags.put("oldTitle", taskObj.getTitle());
                updateFlags.put("newTitle", taskRequestDTO.getTitle());
            }

            if (taskObj.getPriority() != null && !taskObj.getPriority().equals(taskRequestDTO.getPriority())) {
                updateFlags.put("oldPriority", taskObj.getPriority().name());
                updateFlags.put("newPriority", taskRequestDTO.getPriority().name());
            }

            if (taskObj.getPriority() != null && !taskObj.getPriority().equals(taskRequestDTO.getPriority())) {
                updateFlags.put("oldPriority", taskObj.getPriority().name());
                updateFlags.put("newPriority", taskRequestDTO.getPriority().name());
            }

            if (taskObj.getStatus() != null && !taskObj.getStatus().equals(taskRequestDTO.getStatus())) {
                updateFlags.put("oldStatus", taskObj.getStatus().name());
                updateFlags.put("newStatus", taskRequestDTO.getStatus().name());
            }

            if (taskObj.getDueDate() != null && !taskObj.getDueDate().equals(taskRequestDTO.getDueDate())) {
                updateFlags.put("oldDueDate", StringDateConverters.convertDateToString(taskObj.getDueDate(), GENERAL_DATE_TIME_FORMAT.getValue()));
                updateFlags.put("newDueDate", StringDateConverters.convertDateToString(taskRequestDTO.getDueDate(), GENERAL_DATE_TIME_FORMAT.getValue()));
            }
        }
        return updateFlags;
    }

    private void validateCreateTaskRequestParams(TaskRequestDTO taskRequestDTO) {
        List<String> errorMessages = new ArrayList<>();

        if (StringUtils.isBlank(taskRequestDTO.getTitle())) {
            errorMessages.add("message.error.title.input");
        }
        if (taskRequestDTO.getStatus() == null) {
            errorMessages.add("message.error.status.input");
        }

        if (taskRequestDTO.getPriority() == null) {
            errorMessages.add("message.error.priority.input");
        }

        if (taskRequestDTO.getDueDate() == null) {
            errorMessages.add("message.error.dueDate.input");
        }

        if (taskRequestDTO.getDueDate() != null && taskRequestDTO.getDueDate().before(new Date())) {
            errorMessages.add("message.error.invalid.input.dueDate");
        }

        if (!errorMessages.isEmpty()) {
            throw new TaskRequestInputException(errorMessages);
        }
    }

    private void validateUpdateTaskRequestParams(TaskRequestDTO taskRequestDTO) {
        List<String> errorMessages = new ArrayList<>();

        if (taskRequestDTO.getDueDate() != null && taskRequestDTO.getDueDate().before(new Date())) {
            errorMessages.add("message.error.invalid.input.dueDate");
        }

        if (!errorMessages.isEmpty()) {
            throw new TaskRequestInputException(errorMessages);
        }
    }

}