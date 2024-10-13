package banquemisr.challenge05.task.management.service.schedule;

import banquemisr.challenge05.task.management.service.dao.impl.TaskDaoImpl;
import banquemisr.challenge05.task.management.service.dto.TaskEventDTO;
import banquemisr.challenge05.task.management.service.enums.DateFormats;
import banquemisr.challenge05.task.management.service.model.LoginUser;
import banquemisr.challenge05.task.management.service.model.Task;
import banquemisr.challenge05.task.management.service.util.StringDateConverters;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.MessageSource;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Locale;

@Component
@RequiredArgsConstructor
@Slf4j
public class TasksScheduler {

    private final String SCHEDULER_CRON_VALUE = "0 * * * * *";
    private final TaskDaoImpl taskDao;
    @Resource(name = "messages_bundle")
    private final MessageSource messageSource;

    private final KafkaTemplate<String, TaskEventDTO> kafkaTemplate;

    @Value("${spring.kafka.template.default-topic}")
    private String kafkaDefaultTopic;


    @Scheduled(cron = SCHEDULER_CRON_VALUE)
    @SchedulerLock(name = "tasksScheduledTask", lockAtLeastFor = "PT30S", lockAtMostFor = "PT1M")
    @Caching(evict = {@CacheEvict(value = {"tasks"}, key = "#root.methodName", allEntries = true),
            @CacheEvict(value = {"taskHistory"}, key = "#root.methodName", allEntries = true)})
    public void checkTasksDueDate() {

        List<Task> dueTasks = taskDao.findByDueDateAndEmailNotificationNotSent();

        if (!dueTasks.isEmpty()) {
            log.info("Tasks due within 2 days: \n");
            for (Task task : dueTasks) {
                log.info("{} - Due on: {} \n", task.getTitle(), task.getDueDate());

                sendEmail(task);

                task.getNotification().setEmailNotificationSentDate(new Date());
                task.getNotification().setEmailNotificationSent(true);
                log.info("Email to: {} sent successfully.", task.getCreatedBy().getUsername());
                taskDao.updateTask(task, new LoginUser("scheduler.system.user"), String.valueOf(Locale.ENGLISH));
            }
        } else {
            log.info("No tasks due within the next 2 days.");
        }
    }

    private void sendEmail(Task taskObj) {
        kafkaTemplate.send(kafkaDefaultTopic, new TaskEventDTO(
                taskObj.getCreatedBy().getEmail()
                , taskObj.getTitle()
                , StringDateConverters.convertDateToString(taskObj.getDueDate(), DateFormats.GENERAL_DATE_FORMAT.getValue())
                , taskObj.getCreatedBy().getUserFullName()
                , taskObj.getCreatedBy().getUsername()
        ));
    }

}
