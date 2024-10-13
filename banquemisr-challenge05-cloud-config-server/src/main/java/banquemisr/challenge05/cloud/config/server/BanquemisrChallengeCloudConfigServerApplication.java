package banquemisr.challenge05.cloud.config.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class BanquemisrChallengeCloudConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BanquemisrChallengeCloudConfigServerApplication.class, args);
	}

}
