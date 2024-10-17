package banquemisr.challenge05.task.management.service.repository;

import banquemisr.challenge05.task.management.service.model.TaskHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface HistoryRepository {

    TaskHistory save(TaskHistory history);

    void delete(TaskHistory taskHistory);

    Page<TaskHistory> findByTaskTitleAndDueDate(String taskTitle, Date taskDueDateFrom, Date taskDueDateTo, Pageable page);
}
