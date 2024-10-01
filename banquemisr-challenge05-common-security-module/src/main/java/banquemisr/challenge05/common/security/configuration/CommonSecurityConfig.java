package banquemisr.challenge05.common.security.configuration;

import banquemisr.challenge05.common.security.converter.CommonJwtAuthenticationConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class CommonSecurityConfig {

    @Value("${spring.security.oauth2.resourceserver.jwks-uri}")
    private String oauth2ResourceServerJWKSUri;

    @Autowired
    CommonJwtAuthenticationConverter commonJwtAuthenticationConverter;

    @Bean
    public SecurityFilterChain commonSecurityWebFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.oauth2ResourceServer(httpSecurityOAuth2ServerConfigurer -> httpSecurityOAuth2ServerConfigurer
                .jwt(jwtConfigurer -> jwtConfigurer.jwkSetUri(oauth2ResourceServerJWKSUri)
                        .jwtAuthenticationConverter(commonJwtAuthenticationConverter)));

        httpSecurity.authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
                authorizationManagerRequestMatcherRegistry.requestMatchers("/actuator/**").permitAll()
                        .anyRequest().authenticated());
        return httpSecurity.build();
    }
}