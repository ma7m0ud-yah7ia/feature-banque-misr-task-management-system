package banquemisr.challenge05.task.management.service.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;

@Document
@Data
@RequiredArgsConstructor
public class BaseModel {

    @MongoId
    private String id;

    @Field(name = "exposedId", targetType = FieldType.STRING)
    private String exposedId;

    private LoginUser createdBy;

    private LoginUser updatedBy;

    @Field(name = "createdAt", targetType = FieldType.DATE_TIME)
    private Date createdAt;

    @Field(name = "updatedAt", targetType = FieldType.DATE_TIME)
    private Date updatedAt;
}
