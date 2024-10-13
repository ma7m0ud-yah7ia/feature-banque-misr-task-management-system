package banquemisr.challenge05.email.service.service;

import banquemisr.challenge05.email.service.dto.TaskEventDTO;

public interface EmailService {
    boolean sendEmail(String emailFrom, TaskEventDTO taskEventDTO);
}
