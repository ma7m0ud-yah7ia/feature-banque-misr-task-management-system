package banquemisr.challenge05.task.management.service.repository.impl;

import banquemisr.challenge05.task.management.service.model.Task;
import banquemisr.challenge05.task.management.service.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TaskRepositoryImpl implements TaskRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public Task findByTitle(String title) {
        Query query = new Query();
        query.addCriteria(Criteria.where("title").regex(title));
        return (Task) mongoTemplate.find(query, Task.class);
    }

    @Override
    public Page<Task> findByTitleDescStatusDueDate(String taskTitle, String taskStatus, String taskDesc, String taskDueDate, boolean isDeleted, Pageable page) {

        Query query = new Query();

        query.addCriteria(Criteria.where("isDeleted").is(isDeleted));

        if (taskTitle != null && !taskTitle.isEmpty())
            query.addCriteria(Criteria.where("title").regex(taskTitle));

        if (taskStatus != null && !taskStatus.isEmpty())
            query.addCriteria(Criteria.where("status").regex(taskStatus));

        if (taskDesc != null && !taskDesc.isEmpty())
            query.addCriteria(Criteria.where("description").regex(taskDesc));

        if (taskDueDate != null && !taskDueDate.isEmpty())
            query.addCriteria(Criteria.where("dueDate").regex(taskDueDate));

        long totalCount = mongoTemplate.count(query, Task.class);

        query.with(page);

        List<Task> result = mongoTemplate.find(query, Task.class);

        return PageableExecutionUtils.getPage(result, page, () -> totalCount);
    }

    @Override
    public Task save(Task task) {
        return mongoTemplate.save(task);
    }

    @Override
    public Task update(Task task) {
        //Update update = new Update();
        //update.set("",);
        return mongoTemplate.save(task);
    }

    @Override
    public Task delete(Task task) {
        task.setDeleted(true);
        return mongoTemplate.save(task);
    }
}
