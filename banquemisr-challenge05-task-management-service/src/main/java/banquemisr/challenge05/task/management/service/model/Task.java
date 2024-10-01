package banquemisr.challenge05.task.management.service.model;

import banquemisr.challenge05.task.management.service.enums.TaskPriority;
import banquemisr.challenge05.task.management.service.enums.TaskStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Document(collection = "Tasks")
@Data
@RequiredArgsConstructor
public class Task extends BaseModel {

    @TextIndexed
    @Indexed(name = "title_unique_index", unique = true)
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

    @DBRef
    private AppUser user;

    @Field(name = "isDeleted", targetType = FieldType.BOOLEAN)
    private boolean isDeleted = false;
}
