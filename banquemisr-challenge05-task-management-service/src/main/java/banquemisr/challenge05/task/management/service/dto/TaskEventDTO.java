package banquemisr.challenge05.task.management.service.dto;

import lombok.Data;

import java.util.Map;

@Data
public class TaskEventDTO {
    private String emailTo;
    private String title;
    private String priority;
    private String dueDate;
    private String userFullName;
    private String userUsername;
    private Map<String, String> updateFlags;

    public TaskEventDTO() {
    }

    public TaskEventDTO(String emailTo, String title, String priority, String dueDate,
                        String userFullName, String userUsername,
                        Map<String, String> updateFlags) {
        this.emailTo = emailTo;
        this.title = title;
        this.priority = priority;
        this.dueDate = dueDate;
        this.userFullName = userFullName;
        this.userUsername = userUsername;
        this.updateFlags = updateFlags;
    }

    public TaskEventDTO(String emailTo, String title, String dueDate, String userFullName, String username) {
        this.emailTo = emailTo;
        this.title = title;
        this.dueDate = dueDate;
        this.userFullName = userFullName;
        this.userUsername = username;

    }
}
