package banquemisr.challenge05.email.service.constant;

import lombok.Getter;

@Getter
public enum SenderEmailConstants {

    MAIL_TRANS_PROTOCOL("mail.transport.protocol"),
    MAIL_SMTP_AUTH("mail.smtp.auth"),
    MAIL_TLS("mail.smtp.starttls.enable");


    private final String value;

    SenderEmailConstants(String value) {
        this.value = value;
    }

}
