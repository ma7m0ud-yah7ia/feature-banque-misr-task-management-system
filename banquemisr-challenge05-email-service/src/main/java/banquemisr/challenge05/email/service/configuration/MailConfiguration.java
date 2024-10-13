package banquemisr.challenge05.email.service.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

import static banquemisr.challenge05.email.service.constant.SenderEmailConstants.*;

@Configuration
public class MailConfiguration {

    @Value("${spring.google.mail.host}")
    private String mailHost;

     @Value("${spring.google.mail.port}")
    private String mailPort;

     @Value("${spring.google.mail.username}")
    private String mailUsername;

     @Value("${spring.google.mail.password}")
    private String mailPassword;

     @Value("${spring.google.mail.properties.mail.smtp.auth}")
    private String mailSMTPAuth;

    @Value("${spring.google.mail.properties.mail.smtp.starttls.enable}")
    private String mailSMTPTLS;

    @Value("${spring.google.mail.transport.protocol}")
    private String mailTransportProtocol;


    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(mailHost);
        mailSender.setPort(Integer.parseInt(mailPort));
        mailSender.setUsername(mailUsername);
        mailSender.setPassword(mailPassword);

        Properties props = mailSender.getJavaMailProperties();
        props.put(MAIL_TRANS_PROTOCOL.getValue(), mailTransportProtocol);
        props.put(MAIL_SMTP_AUTH.getValue(), mailSMTPAuth);
        props.put(MAIL_TLS.getValue(), mailSMTPTLS);
        props.put("mail.debug", "true");

        return mailSender;
    }
}
