package banquemisr.challenge05.task.management.service.model;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Document(collection = "Roles")
@RequiredArgsConstructor
@Data
public class Role extends BaseModel {

    private String roleName;
}
