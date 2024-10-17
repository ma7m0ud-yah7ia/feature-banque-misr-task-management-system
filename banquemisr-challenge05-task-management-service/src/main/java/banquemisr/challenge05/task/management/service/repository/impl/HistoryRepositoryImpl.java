package banquemisr.challenge05.task.management.service.repository.impl;

import banquemisr.challenge05.task.management.service.model.TaskHistory;
import banquemisr.challenge05.task.management.service.repository.HistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.Date;
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
    public Page<TaskHistory> findByTaskTitleAndDueDate(String taskTitle, Date taskDueDateFrom, Date taskDueDateTo, Pageable page) {
        Query query = new Query();

        if (taskDueDateFrom != null && taskDueDateTo != null)
            query.addCriteria(Criteria.where("task.dueDate").gte(taskDueDateFrom).lte(taskDueDateTo));

        if (taskTitle != null && !taskTitle.isEmpty())
            query.addCriteria(Criteria.where("task.title").regex(taskTitle));

        long totalCount = mongoTemplate.count(query, TaskHistory.class);

        query.with(page);

        List<TaskHistory> result = mongoTemplate.find(query, TaskHistory.class);

        return PageableExecutionUtils.getPage(result, page, () -> totalCount);
    }

}
