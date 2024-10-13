package banquemisr.challenge05.task.management.service.model;

import banquemisr.challenge05.task.management.service.enums.TaskPriority;
import banquemisr.challenge05.task.management.service.enums.TaskStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Document(collection = "Tasks")
@Data
@NoArgsConstructor
public class Task extends BaseModel {

    @Field(name = "title", targetType = FieldType.STRING)
    private String title;

    @Field(name = "desc", targetType = FieldType.STRING)
    private String description;

    @Field(name = "status", targetType = FieldType.STRING)
    private TaskStatus status;

    @Field(name = "priority", targetType = FieldType.STRING)
    private TaskPriority priority;

    @Field(name = "dueDate", targetType = FieldType.DATE_TIME)
    private Date dueDate;

    private Notification notification = new Notification();

    public Task(Task task) {
        this.setTitle(task.getTitle());
        this.setDescription(task.getDescription());
        this.setStatus(task.getStatus());
        this.setPriority(task.getPriority());
        this.setDueDate(task.getDueDate());
        this.setNotification(task.getNotification());
    }
}
