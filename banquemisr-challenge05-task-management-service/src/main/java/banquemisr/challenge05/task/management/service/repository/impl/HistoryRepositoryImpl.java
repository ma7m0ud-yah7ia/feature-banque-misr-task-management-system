package banquemisr.challenge05.task.management.service.repository.impl;

import banquemisr.challenge05.task.management.service.model.TaskHistory;
import banquemisr.challenge05.task.management.service.repository.HistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class HistoryRepositoryImpl implements HistoryRepository {
    private final MongoTemplate mongoTemplate;

    @Override
    public TaskHistory save(TaskHistory history) {
        return mongoTemplate.save(history);
    }

    @Override
    public void delete(TaskHistory taskHistory) {
        mongoTemplate.remove(taskHistory);
    }

    @Override
    public List<TaskHistory> findAllByTaskExposedId(String exposedId) {
        return List.of();
    }
}
