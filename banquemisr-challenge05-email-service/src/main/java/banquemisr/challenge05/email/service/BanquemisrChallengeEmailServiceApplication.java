package banquemisr.challenge05.email.service;

import banquemisr.challenge05.email.service.dto.TaskEventDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
@Slf4j
public class BanquemisrChallengeEmailServiceApplication {

    private final String KAFKA_DEFAULT_TOPIC = "sendingEmailTopic";


    public static void main(String[] args) {
        SpringApplication.run(BanquemisrChallengeEmailServiceApplication.class, args);
    }

    @KafkaListener(topics = KAFKA_DEFAULT_TOPIC)
    public void handleSendEmailNotification(TaskEventDTO taskEventDTO) {
        log.info("Received Task important message: {}", taskEventDTO.getEmailTo());
        //TODO: SEND EMAIL
    }
}
