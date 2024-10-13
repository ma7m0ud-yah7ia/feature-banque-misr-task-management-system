package banquemisr.challenge05.task.management.service;

import banquemisr.challenge05.task.management.service.dao.impl.TaskDaoImpl;
import banquemisr.challenge05.task.management.service.enums.TaskPriority;
import banquemisr.challenge05.task.management.service.enums.TaskStatus;
import banquemisr.challenge05.task.management.service.model.LoginUser;
import banquemisr.challenge05.task.management.service.model.Task;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@SpringBootApplication
@EnableMongoRepositories
@EnableCaching
@EnableScheduling
@EnableSchedulerLock(defaultLockAtLeastFor = "PT30S", defaultLockAtMostFor = "PT1M")
public class BanqueMisrChallengeTaskManagementServiceApplication implements CommandLineRunner {

    @Autowired
    private TaskDaoImpl taskDao;

    public static void main(String[] args) {
        SpringApplication.run(BanqueMisrChallengeTaskManagementServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        List<Task> tasks = taskDao.findAll();
        if (tasks == null || tasks.isEmpty()) {
            // Insert testable data for regular.user
            for (int i = 1; i <= 30; i++) {
                Task task = new Task();
                task.setTitle("task-" + i);
                task.setDescription("task -" + i + " description");
                task.setStatus(TaskStatus.TODO);
                task.setPriority(TaskPriority.HIGH);
                task.setDueDate(getCustomDate(new Date(), 3));
                task.setCreatedAt(new Date());
                task.setExposedId(UUID.randomUUID().toString());
                task.setCreatedBy(new LoginUser("regular.user"));
                taskDao.saveTask(task, "en");
                log.info("Task with id {}, created successfully", task.getExposedId());
            }

            // Insert testable data for admin.user
            for (int i = 30; i <= 60; i++) {
                Task task = new Task();
                task.setTitle("task-" + i);
                task.setDescription("task -" + i + " description");
                task.setStatus(TaskStatus.TODO);
                task.setPriority(TaskPriority.HIGH);
                task.setDueDate(getCustomDate(new Date(), 3));
                task.setCreatedAt(new Date());
                task.setExposedId(UUID.randomUUID().toString());
                task.setCreatedBy(new LoginUser("admin.user"));
                taskDao.saveTask(task, "en");
                log.info("Task with id {}, created successfully", task.getExposedId());
            }
        }
    }

    private Date getCustomDate(Date dueDate, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dueDate);
        calendar.add(Calendar.DAY_OF_MONTH, days);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
}
