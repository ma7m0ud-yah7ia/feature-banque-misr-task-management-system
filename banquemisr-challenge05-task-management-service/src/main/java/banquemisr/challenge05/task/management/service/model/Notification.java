package banquemisr.challenge05.task.management.service.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;

@RequiredArgsConstructor
@Getter
@Setter
public class Notification {
    private boolean emailNotificationSent;
    private String emailNotificationSentDueDate;
    private Date emailNotificationSentDate;
}
