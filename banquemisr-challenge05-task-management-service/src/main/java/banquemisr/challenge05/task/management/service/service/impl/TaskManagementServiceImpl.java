package banquemisr.challenge05.task.management.service.service.impl;

import banquemisr.challenge05.task.management.service.Util.TasksMapper;
import banquemisr.challenge05.task.management.service.dto.*;
import banquemisr.challenge05.task.management.service.exceptions.TasksDuplicateKeyException;
import banquemisr.challenge05.task.management.service.model.AppUser;
import banquemisr.challenge05.task.management.service.model.Task;
import banquemisr.challenge05.task.management.service.repository.TaskRepository;
import banquemisr.challenge05.task.management.service.service.TaskManagementService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskManagementServiceImpl implements TaskManagementService {

    private final TaskRepository taskRepository;
    private final TasksMapper tasksMapper;

    @Resource(name = "messages_bundle")
    private final MessageSource messageSource;

    private final KafkaTemplate<String, TaskEventDTO> kafkaTemplate;

    @Value("${spring.kafka.template.default-topic}")
    private String kafkaDefaultTopic;

    public TaskResponseDTO createTask(TaskRequestDTO tasksRequestDTO, String lang) throws TasksDuplicateKeyException {
        TaskResponseDTO tasksResponseDTO = saveTask(tasksRequestDTO);
        Locale locale = new Locale(lang);
        tasksResponseDTO.setMessage(messageSource.getMessage("message.task.created.successfully", null, locale));
        return tasksResponseDTO;
    }

    public TaskResponseDTO updateTask(TaskRequestDTO taskRequestDTO) {

        AppUserDTO appUserDTO = new AppUserDTO();

        Task taskObj = taskRepository.findByTitle(taskRequestDTO.getTitle());

        AppUser user = tasksMapper.mapAppUserDTOToAppUser(appUserDTO);
        taskObj.setUpdatedAt(new Date());
        taskObj.setUpdatedBy(user);

        TaskResponseDTO tasksResponseDTO = tasksMapper.mapTaskModelToTasksResponseDTO(
                taskRepository.update(tasksMapper.mapTasksRequestDTOToTaskModel(taskRequestDTO, taskObj)));

        //BEGIN
        String bodyMessage = "The task with Title: " + tasksResponseDTO.getTask().getTitle() + ", its dueDate changed from: " + taskObj.getDueDate() + "to: " + tasksResponseDTO.getTask().getDueDate();

        sendEmail("m.h.yahia007@gmail.com", bodyMessage);
        // END
        if (!Objects.equals(taskObj.getDueDate(), taskRequestDTO.getDueDate())) {
            //TODO: Send email
            //            String bodyMessage = "The task with Title: " + tasksResponseDTO.getTask().getTitle() +
            //                    ", its dueDate changed from: " + taskObj.getDueDate() + "to: " + tasksResponseDTO.getTask().getDueDate();
            //            sendEmail("m.h.yahia007@gmail.com",bodyMessage);
        }

        if (!Objects.equals(taskObj.getPriority(), taskRequestDTO.getPriority())) {
            //TODO: Send email
            //            String bodyMessage = "The task with Title: " + tasksResponseDTO.getTask().getTitle() +
            //                    ", its priority changed from: " + taskObj.getPriority() + "to: " + tasksResponseDTO.getTask().getPriority();
            //            sendEmail("",bodyMessage);
        }
        tasksResponseDTO.setMessage("Task updated successfully..!");

        return tasksResponseDTO;

    }

    private void sendEmail(String emailTo, String bodyMessage) {
        kafkaTemplate.send(kafkaDefaultTopic, new TaskEventDTO());
    }

    public TasksSearchResponseDTO getTaskByCriteria(String taskTitle, String taskStatus, String taskDesc, String taskDueDate, int page, int sizePerPage) {
        int pageNumber = page - 1;
        Page<Task> tasksPage = taskRepository.findByTitleDescStatusDueDate(taskTitle, taskStatus, taskDesc, taskDueDate, false, PageRequest.of(pageNumber, sizePerPage, Sort.by("dueDate").descending()));
        TasksSearchResponseDTO tasksSearchResponseDTO = new TasksSearchResponseDTO();
        tasksPage.getContent().forEach(task -> {
            tasksSearchResponseDTO.getTasks().add(tasksMapper.mapTaskModelToTasksResponseDTO(task).getTask());
        });

        tasksSearchResponseDTO.setPage(page);
        tasksSearchResponseDTO.setTotalPages(tasksPage.getTotalPages());
        tasksSearchResponseDTO.setTotalSize((int) tasksPage.getTotalElements());
        return tasksSearchResponseDTO;
    }

    @Transactional
    public TaskResponseDTO deleteTask(TaskRequestDTO tasksRequestDTO) {
        Task task = taskRepository.findByTitle(tasksRequestDTO.getTitle());
        TaskResponseDTO tasksResponseDTO = tasksMapper.mapTaskModelToTasksResponseDTO(taskRepository.delete(task));
        tasksResponseDTO.setMessage("Task deleted successfully..!");
        return tasksResponseDTO;

    }

    private TaskResponseDTO saveTask(TaskRequestDTO tasksRequestDTO) {
        Task taskObj = Objects.requireNonNull(tasksMapper.mapTasksRequestDTOToTaskModel(tasksRequestDTO));
        AppUserDTO appUserDTO = new AppUserDTO();

        AppUser appUser = tasksMapper.mapAppUserDTOToAppUser(appUserDTO);

        taskObj.setUser(appUser);
        taskObj.setCreatedBy(appUser);
        taskObj.setCreatedAt(new Date());
        taskObj.setExposedId(UUID.randomUUID().toString());

        taskRepository.save(taskObj);
        return tasksMapper.mapTaskModelToTasksResponseDTO(taskObj);
    }
}
