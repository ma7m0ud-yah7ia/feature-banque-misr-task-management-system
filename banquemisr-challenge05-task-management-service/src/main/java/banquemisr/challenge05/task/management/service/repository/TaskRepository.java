package banquemisr.challenge05.task.management.service.repository;

import banquemisr.challenge05.task.management.service.model.LoginUser;
import banquemisr.challenge05.task.management.service.model.Task;
import banquemisr.challenge05.task.management.service.model.TaskHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.Optional;


public interface TaskRepository {

    Optional<Task> findByExposedIdAndUser(String exposedId, LoginUser user);

    Page<Task> findByTitleDescStatusPriorityDueDate(String taskTitle, String taskStatus, String taskPriority, String taskDesc, Date taskDueDateFrom,
                                                    Date taskDueDateTo, boolean isAdmin, boolean displayAll, LoginUser user, Pageable page);

    Task save(Task task);

    void delete(Task task);

    List<Task> findByDueDateAndEmailNotificationNotSent(String dueDate);

    Page<TaskHistory> findByTaskTitleAndDueDate(String taskTitle, Date taskDueDateFrom, Date taskDueDateTo, Pageable dueDate);

    List<Task> findAll();
}
