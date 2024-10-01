package banquemisr.challenge05.task.management.service.repository;

import banquemisr.challenge05.task.management.service.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface TaskRepository {
    Task findByTitle(String title);

    Page<Task> findByTitleDescStatusDueDate(String taskTitle, String taskStatus, String taskDesc, String taskDueDate, boolean isDeleted, Pageable page);

    Task save(Task task);

    Task update(Task task);

    Task delete(Task taskObj);
}
