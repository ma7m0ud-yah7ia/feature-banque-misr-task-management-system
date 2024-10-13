package banquemisr.challenge05.task.management.service.dao.impl;

import banquemisr.challenge05.task.management.service.dao.TaskDao;
import banquemisr.challenge05.task.management.service.enums.DateFormats;
import banquemisr.challenge05.task.management.service.enums.TaskHistoryOperationType;
import banquemisr.challenge05.task.management.service.enums.TaskPriority;
import banquemisr.challenge05.task.management.service.enums.TaskStatus;
import banquemisr.challenge05.task.management.service.exception.TaskResourceNotFoundException;
import banquemisr.challenge05.task.management.service.model.LoginUser;
import banquemisr.challenge05.task.management.service.model.Notification;
import banquemisr.challenge05.task.management.service.model.Task;
import banquemisr.challenge05.task.management.service.model.TaskHistory;
import banquemisr.challenge05.task.management.service.repository.HistoryRepository;
import banquemisr.challenge05.task.management.service.repository.TaskRepository;
import banquemisr.challenge05.task.management.service.util.StringDateConverters;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskDaoImpl implements TaskDao {

    private final TaskRepository taskRepository;
    private final HistoryRepository historyRepository;

    @Resource(name = "messages_bundle")
    private final MessageSource messageSource;


    @Override
    public Task saveTask(Task task, String lang) {
        Task savedTask;
        try {
            Notification notification = prepareNotification(task);
            task.setNotification(notification);
            savedTask = taskRepository.save(task);
            historyRepository.save(prepareHistoryOperation(task, task.getCreatedBy(), TaskHistoryOperationType.ADD));
            return savedTask;
        } catch (Exception e) {
            log.error(messageSource.getMessage("message.error.task.save", null, new Locale(lang)), e);
            throw new TaskResourceNotFoundException("message.error.task.save");
        }
    }

    @Override
    public Task updateTask(Task task, LoginUser loginUser, String lang) {
        Task updatedTask;
        try {
            Notification notification = prepareNotification(task);
            task.setNotification(notification);
            updatedTask = taskRepository.save(task);
            historyRepository.save(prepareHistoryOperation(task, loginUser, TaskHistoryOperationType.UPDATE));

            return updatedTask;
        } catch (Exception e) {
            log.error(messageSource.getMessage("message.error.task.update", null, new Locale(lang)), e);
            throw new TaskResourceNotFoundException("message.error.task.update");
        }
    }

    @Override
    public Task deletTask(Task task, LoginUser user, String lang) {
        TaskHistory taskHistory = null;
        Task deletedTask = new Task(task);
        try {
            taskRepository.delete(task);
            taskHistory = historyRepository.save(prepareHistoryOperation(deletedTask, user, TaskHistoryOperationType.DELETE));
            return deletedTask;
        } catch (Exception e) {
            historyRepository.delete(taskHistory);
            log.error(messageSource.getMessage("message.error.task.delete", null, new Locale(lang)), e);
            throw new TaskResourceNotFoundException("message.error.task.delete");
        }
    }

    @Override
    public Page<Task> findByTitleDescStatusPriorityDueDate(String taskTitle, String taskStatus, String
            taskPriority, String taskDesc, Date taskDueDateFrom, Date taskDueDateTo, boolean isAdmin, boolean displayAll, LoginUser user, PageRequest pageRequest) {
        return taskRepository.findByTitleDescStatusPriorityDueDate(taskTitle, taskStatus,
                taskPriority, taskDesc, taskDueDateFrom,
                taskDueDateTo, isAdmin, displayAll, user, pageRequest);
    }

    @Override
    public Page<TaskHistory> findByTaskTitleAndDueDate(String taskTitle, Date taskDueDateFrom, Date taskDueDateTo, PageRequest pageRequest) {
        return taskRepository.findByTaskTitleAndDueDate(taskTitle, taskDueDateFrom, taskDueDateTo, pageRequest);
    }

    @Override
    public List<Task> findAll() {
        return taskRepository.findAll();
    }


    @Override
    public Optional<Task> findByExposedIdAndUser(String exposedId, LoginUser user) {
        return taskRepository.findByExposedIdAndUser(exposedId, user);
    }

    @Override
    public List<Task> findByDueDateAndEmailNotificationNotSent() {
        return taskRepository.findByDueDateAndEmailNotificationNotSent(
                StringDateConverters.convertDateToString(getCustomDate(new Date(), 0),
                        DateFormats.GENERAL_DATE_FORMAT.getValue()));
    }

    private TaskHistory prepareHistoryOperation(Task task, LoginUser user, TaskHistoryOperationType operationType) {
        TaskHistory taskHistory = new TaskHistory();
        taskHistory.setOperation(operationType);
        taskHistory.setCreatedAt(new Date());
        taskHistory.setCreatedBy(user);
        taskHistory.setTask(task);
        taskHistory.setExposedId(UUID.randomUUID().toString());
        return taskHistory;
    }

    private Notification prepareNotification(Task task) {
        Notification notification = null;

        Date afterTomorrow = getCustomDate(new Date(), 2);
        if (task.getDueDate().after(afterTomorrow)
                && task.getPriority().equals(TaskPriority.HIGH)
                && !task.getStatus().equals(TaskStatus.DONE)) {
            notification = new Notification();
            notification.setEmailNotificationSent(task.getNotification().isEmailNotificationSent());
            notification.setEmailNotificationSentDate(task.getNotification().getEmailNotificationSentDate());
            notification.setEmailNotificationSentDueDate(StringDateConverters.convertDateToString(getCustomDate(task.getDueDate(), -2), DateFormats.GENERAL_DATE_FORMAT.getValue()));
        }
        return notification;
    }


    private Date getCustomDate(Date dueDate, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dueDate);
        calendar.add(Calendar.DAY_OF_MONTH, days);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }
}
