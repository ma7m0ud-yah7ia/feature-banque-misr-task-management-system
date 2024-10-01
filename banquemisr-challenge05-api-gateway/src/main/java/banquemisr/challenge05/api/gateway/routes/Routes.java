package banquemisr.challenge05.api.gateway.routes;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Routes {

    @Value("${banque-misr-task-management-service.url}")
    private String banquemisrTaskManagementServiceUrl;

    @Bean
    public RouteLocator apiGatewayRouter(RouteLocatorBuilder builder) {
        return builder.routes().route(p ->
                p.path("/banque-misr-task-management-service/**")
                        .uri(banquemisrTaskManagementServiceUrl)
        ).build();
    }
}
