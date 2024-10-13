package banquemisr.challenge05.api.gateway.configuration;

import banquemisr.challenge05.api.gateway.converter.CommonJwtAuthenticationConverter;
import banquemisr.challenge05.api.gateway.exception.APIGatewayException;
import banquemisr.challenge05.api.gateway.redirect.roles.Roles;
import banquemisr.challenge05.api.gateway.redirect.services.PagesPrivilege;
import banquemisr.challenge05.api.gateway.security.CustomServerAuthenticationAccessDeniedHandler;
import banquemisr.challenge05.api.gateway.security.CustomServerAuthenticationEntryPoint;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;

import java.util.Locale;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@RequiredArgsConstructor
@Slf4j
public class CommonSecurityConfig {

    @Value("${spring.security.oauth2.resourceserver.jwks-uri}")
    private String oauth2ResourceServerJWKSUri;

    @Value("${banque-misr-task-management-service.url}")
    private String banquemisrTaskManagementServiceUrl;

    @Resource(name = "messages_bundle")
    private final MessageSource messageSource;

    private final CommonJwtAuthenticationConverter commonJwtAuthenticationConverter;

    private final CustomServerAuthenticationEntryPoint customServerAuthenticationEntryPoint;

    private final CustomServerAuthenticationAccessDeniedHandler customServerAuthenticationAccessDeniedHandler;


    @Bean
    public SecurityWebFilterChain commonSecurityWebFilterChain(ServerHttpSecurity serverHttpSecurity) throws Exception {
        try {
            serverHttpSecurity
                    .oauth2ResourceServer(httpSecurityOAuth2ServerConfigurer -> httpSecurityOAuth2ServerConfigurer
                            .authenticationEntryPoint(customServerAuthenticationEntryPoint)
                            .accessDeniedHandler(customServerAuthenticationAccessDeniedHandler)
                            .jwt(jwtConfigurer -> jwtConfigurer.jwkSetUri(oauth2ResourceServerJWKSUri)
                                    .jwtAuthenticationConverter(new ReactiveJwtAuthenticationConverterAdapter(commonJwtAuthenticationConverter))))
                    .cors(ServerHttpSecurity.CorsSpec::disable)
                    .csrf(ServerHttpSecurity.CsrfSpec::disable);

            serverHttpSecurity
                    .authorizeExchange(authorizationManagerRequestMatcherRegistry ->
                            authorizationManagerRequestMatcherRegistry
                                    .pathMatchers(PagesPrivilege.NO_AUTH_PAGES.getValue()).permitAll()
                                    .pathMatchers(PagesPrivilege.ADMIN_AUTH_PAGES.getValue()).hasAuthority(Roles.ADMIN_ROLES.getValue())
                                    .pathMatchers(PagesPrivilege.USER_AUTH_PAGES.getValue()).hasAuthority(Roles.USER_ROLES.getValue())
                                    .anyExchange().authenticated());

            log.info(messageSource.getMessage("APIGTWY000001", null, Locale.ENGLISH));
        } catch (Exception exception) {
            log.error(messageSource.getMessage("APIGTWY000002", null, Locale.ENGLISH), exception);
            throw new APIGatewayException("APIGTWY000002");
        }
        return serverHttpSecurity.build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withJwkSetUri(oauth2ResourceServerJWKSUri).build();
    }

}