package banquemisr.challenge05.task.management.service.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Document(collection = "AppUser")
@Data
@RequiredArgsConstructor
public class AppUser extends BaseModel {
    @Field(name = "userName", targetType = FieldType.STRING)
    private String userName;
    @DBRef
    private Set<Role> role;
}
