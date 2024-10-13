package banquemisr.challenge05.task.management.service.model;

import banquemisr.challenge05.task.management.service.enums.TaskHistoryOperationType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@Document(collection = "History")
@Setter
@Getter
@RequiredArgsConstructor
public class TaskHistory extends BaseModel {

    @Field(name = "operation", targetType = FieldType.STRING)
    private TaskHistoryOperationType operation;

    private Task task;
}
