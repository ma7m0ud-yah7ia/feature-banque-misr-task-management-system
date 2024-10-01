package banquemisr.challenge05.task.management.service.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.*;

import java.util.Date;

@Document
@Data
@RequiredArgsConstructor
public class BaseModel {

    @MongoId
    private String id;

    @Field(name = "exposedId", targetType = FieldType.STRING)
    private String exposedId;

    @DBRef
    private AppUser createdBy;

    @DBRef
    private AppUser updatedBy;

    @Field(name = "createdAt", targetType = FieldType.DATE_TIME)
    private Date createdAt;

    @Field(name = "updatedAt", targetType = FieldType.DATE_TIME)
    private Date updatedAt;

    @Field(name = "deletedAt", targetType = FieldType.DATE_TIME)
    private Date deletedAt;

}
