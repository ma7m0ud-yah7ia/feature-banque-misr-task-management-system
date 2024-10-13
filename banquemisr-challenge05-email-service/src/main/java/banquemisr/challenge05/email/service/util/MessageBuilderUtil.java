package banquemisr.challenge05.email.service.util;

import banquemisr.challenge05.email.service.dto.TaskEventDTO;

import java.util.Map;

public class MessageBuilderUtil {

    public static String buildUpdateChangesMessage(TaskEventDTO taskEventDTO) {
        StringBuilder messageBuilder = new StringBuilder();

        messageBuilder
                .append("Dear, ")
                .append(taskEventDTO.getUserFullName())
                .append(". \nYou must take into consideration that the task: '")
                .append(taskEventDTO.getTitle())
                .append("' had some changes: \n");

        Map<String, String> updateFlags = taskEventDTO.getUpdateFlags();
        
        // Loop through the update flags and build the message dynamically
        for (Map.Entry<String, String> entry : updateFlags.entrySet()) {
            String field = entry.getKey().replace("old", ""); // Remove 'old' from the key
            String oldValue = entry.getValue();
            String newValue = updateFlags.get("new" + field);

            if (oldValue != null && newValue != null) {
                messageBuilder
                    .append("- The ")
                    .append(field)
                    .append(" changed from: '")
                    .append(oldValue)
                    .append("' to: '")
                    .append(newValue)
                    .append("' \n");
            }
        }

        messageBuilder.append("\nMessage sent automatically by Banque Misr Task Demo System.\n");
        return messageBuilder.toString();
    }
}
