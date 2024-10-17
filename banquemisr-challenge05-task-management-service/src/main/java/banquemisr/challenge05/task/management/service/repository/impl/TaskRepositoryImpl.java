package banquemisr.challenge05.task.management.service.repository.impl;

import banquemisr.challenge05.task.management.service.model.LoginUser;
import banquemisr.challenge05.task.management.service.model.Task;
import banquemisr.challenge05.task.management.service.model.TaskHistory;
import banquemisr.challenge05.task.management.service.repository.TaskRepository;
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
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TaskRepositoryImpl implements TaskRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public Optional<Task> findByExposedIdAndUser(String exposedId, LoginUser user) {
        Query query = new Query();
        query.addCriteria(Criteria.where("exposedId").is(exposedId));

        if (user != null)
            query.addCriteria(Criteria.where("createdBy").is(user));

        return Optional.ofNullable(mongoTemplate.findOne(query, Task.class));
    }

    @Override
    public Page<Task> findByTitleDescStatusPriorityDueDate(String taskTitle, String taskStatus, String taskPriority, String taskDesc,
                                                           Date taskDueDateFrom, Date taskDueDateTo,
                                                           boolean isAdmin, boolean displayAll, LoginUser user, Pageable page) {

        Query query = new Query();

        if (!isAdmin)
            query.addCriteria(Criteria.where("createdBy").is(user));

        if (isAdmin && !displayAll)
            query.addCriteria(Criteria.where("createdBy").is(user));

        if (taskTitle != null && !taskTitle.isEmpty())
            query.addCriteria(Criteria.where("title").regex(taskTitle));

        if (taskStatus != null && !taskStatus.isEmpty())
            query.addCriteria(Criteria.where("status").is(taskStatus));

        if (taskPriority != null && !taskPriority.isEmpty())
            query.addCriteria(Criteria.where("priority").is(taskPriority));

        if (taskDesc != null && !taskDesc.isEmpty())
            query.addCriteria(Criteria.where("description").regex(taskDesc));

        if (taskDueDateFrom != null && taskDueDateTo != null)
            query.addCriteria(Criteria.where("dueDate").gte(taskDueDateFrom).lte(taskDueDateTo));

        long totalCount = mongoTemplate.count(query, Task.class);

        query.with(page);

        List<Task> result = mongoTemplate.find(query, Task.class);

        return PageableExecutionUtils.getPage(result, page, () -> totalCount);
    }

    @Override
    public List<Task> findAll() {
        return mongoTemplate.findAll(Task.class);
    }


    @Override
    public Task save(Task task) {
        return mongoTemplate.save(task);
    }

    @Override
    public void delete(Task task) {
        mongoTemplate.remove(task);
    }

    @Override
    public List<Task> findByDueDateAndEmailNotificationNotSent(String emailNotificationSentDueDate) {
        Query query = new Query();
        query.addCriteria(Criteria.where("notification.emailNotificationSentDueDate").is(emailNotificationSentDueDate));
        query.addCriteria(Criteria.where("notification.emailNotificationSent").is(false));

        return mongoTemplate.find(query, Task.class);
    }
}
