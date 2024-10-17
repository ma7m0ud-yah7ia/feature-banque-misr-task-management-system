package banquemisr.challenge05.task.management.service.dao;

import banquemisr.challenge05.task.management.service.dto.PageDTO;
import banquemisr.challenge05.task.management.service.model.LoginUser;
import banquemisr.challenge05.task.management.service.model.Task;
import banquemisr.challenge05.task.management.service.model.TaskHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface TaskDao {
    Task saveTask(Task task, String lang);

    Task updateTask(Task task, LoginUser loginUser, String lang);

    Task deletTask(Task task, LoginUser user, String lang);

    PageDTO<Task> findByTitleDescStatusPriorityDueDate(String taskTitle, String taskStatus, String taskPriority,
                                                    String taskDesc, Date taskDueDateFrom,
                                                    Date taskDueDateTo, boolean isAdmin, boolean displayAll, LoginUser user, PageRequest pageRequest);

    Optional<Task> findByExposedIdAndUser(String exposedId, LoginUser user);

    List<Task> findByDueDateAndEmailNotificationNotSent();

    PageDTO<TaskHistory> findByTaskTitleAndDueDate(String taskTitle, Date taskDueDateFrom, Date taskDueDateTo, PageRequest dueDate);

    List<Task> findAll();
}
