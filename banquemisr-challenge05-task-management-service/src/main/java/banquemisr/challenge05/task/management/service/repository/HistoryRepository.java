package banquemisr.challenge05.task.management.service.repository;

import banquemisr.challenge05.task.management.service.model.TaskHistory;

import java.util.List;

public interface HistoryRepository {
    TaskHistory save(TaskHistory history);
    void delete(TaskHistory taskHistory);
   List<TaskHistory> findAllByTaskExposedId(String exposedId);
}
