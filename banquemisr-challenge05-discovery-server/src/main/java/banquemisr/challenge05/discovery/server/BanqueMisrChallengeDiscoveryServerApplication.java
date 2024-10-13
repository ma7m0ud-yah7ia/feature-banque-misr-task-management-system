package banquemisr.challenge05.discovery.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class BanqueMisrChallengeDiscoveryServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BanqueMisrChallengeDiscoveryServerApplication.class, args);
    }
}