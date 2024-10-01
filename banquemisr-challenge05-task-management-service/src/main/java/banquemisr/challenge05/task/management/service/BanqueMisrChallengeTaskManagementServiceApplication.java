package banquemisr.challenge05.task.management.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class BanqueMisrChallengeTaskManagementServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BanqueMisrChallengeTaskManagementServiceApplication.class, args);
	}

}
