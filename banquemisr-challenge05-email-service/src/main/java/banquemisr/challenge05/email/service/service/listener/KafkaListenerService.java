package banquemisr.challenge05.email.service.service.listener;

import banquemisr.challenge05.email.service.dto.TaskEventDTO;
import banquemisr.challenge05.email.service.service.impl.EmailServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import static banquemisr.challenge05.email.service.constant.KafkaTopics.KAFKA_DEFAULT_TOPIC;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaListenerService {

    private final EmailServiceImpl emailService;

    @Value("${spring.google.mail.username}")
    private String emailFrom;

    @KafkaListener(topics = KAFKA_DEFAULT_TOPIC)
    public void handleSendEmailNotification(TaskEventDTO taskEventDTO, Acknowledgment acknowledgment) {
        log.debug("TASKS_UPDATES_SYSTEM_EMAIL : {}", emailFrom);
        log.info("Received Task important message: {}", taskEventDTO.getEmailTo());

        try {
            boolean isSent = emailService.sendEmail(emailFrom, taskEventDTO);

            log.info("Email sent to user: {}, with taskTitle: {} was sent successfully", taskEventDTO.getUserUsername(), taskEventDTO.getTitle());
            if (isSent)
                acknowledgment.acknowledge();
        } catch (Exception exception) {
            log.error("Error while sending the email {0}", exception.getCause());
        }
    }
}
