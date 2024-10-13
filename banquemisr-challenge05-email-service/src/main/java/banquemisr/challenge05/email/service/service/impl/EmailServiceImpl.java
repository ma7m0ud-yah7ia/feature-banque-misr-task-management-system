package banquemisr.challenge05.email.service.service.impl;

import banquemisr.challenge05.email.service.dto.TaskEventDTO;
import banquemisr.challenge05.email.service.service.EmailService;
import banquemisr.challenge05.email.service.util.MessageBuilderUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    @Override
    public boolean sendEmail(String emailFrom, TaskEventDTO taskEventDTO) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(emailFrom);
        message.setTo(taskEventDTO.getEmailTo());
        String messageSubject = "Banque Misr System E-Mail";
        message.setSubject(messageSubject);

        if (taskEventDTO.getUpdateFlags() != null && !taskEventDTO.getUpdateFlags().isEmpty()) {
            message.setText(MessageBuilderUtil.buildUpdateChangesMessage(taskEventDTO));
        } else {
            message.setText(buildScheduledMessage(taskEventDTO));
        }

        log.info("The email message is: {}", message);
        javaMailSender.send(message);
        log.info("Email sent successfully");
        return true;
    }

    private String buildScheduledMessage(TaskEventDTO taskEventDTO) {
        return "Dear, " +
                taskEventDTO.getUserFullName() +
                ". \n you must take into consideration that the task: '" +
                taskEventDTO.getTitle() +
                "' due date will coming soon, and will be on: '" +
                taskEventDTO.getDueDate() + "' \n" +
                "\n Message sent automatically by Banque Misr Task Demo System. \n";

    }

}
