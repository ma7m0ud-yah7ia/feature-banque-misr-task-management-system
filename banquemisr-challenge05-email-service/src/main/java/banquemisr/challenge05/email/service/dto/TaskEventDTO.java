package banquemisr.challenge05.email.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.kafka.common.protocol.types.Field;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskEventDTO {
    private String emailTo;
    private String title;
    private String priority;
    private Field.Str dueDate;
}
