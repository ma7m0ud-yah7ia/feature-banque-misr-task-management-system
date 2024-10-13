package banquemisr.challenge05.api.gateway.routes;

import banquemisr.challenge05.api.gateway.filter.AuthenticationFilter;
import banquemisr.challenge05.api.gateway.redirect.services.BaseServicesUrls;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Routes {

    @Value("${banque-misr-task-management-service.url}")
    private String banquemisrTaskManagementServiceUrl;

    @Value("${email-service.url}")
    private String banquemisrEmailServiceUrl;

    private final AuthenticationFilter authenticationFilter;

    public Routes(AuthenticationFilter authenticationFilter) {
        this.authenticationFilter = authenticationFilter;
    }

    @Bean
    public RouteLocator apiGatewayRouter(RouteLocatorBuilder builder) {
        return builder.routes().route(taskManagementService ->
                        taskManagementService.path(BaseServicesUrls.TASK_MANAGEMENT_SERVICE_BASE_URL.trim() + "/**")
                                .filters(filterSpec -> filterSpec.rewritePath(BaseServicesUrls.TASK_MANAGEMENT_SERVICE_BASE_URL.trim() + "/(?<remaining>.*)"
                                                , "/${remaining}")
                                        .filter(authenticationFilter))
                                .uri(banquemisrTaskManagementServiceUrl))

                .route(emailService ->
                        emailService.path(BaseServicesUrls.EMAILS_SERVICE_BASE_URL + "/**")
                                .uri(banquemisrEmailServiceUrl))

                .build();
    }
}
