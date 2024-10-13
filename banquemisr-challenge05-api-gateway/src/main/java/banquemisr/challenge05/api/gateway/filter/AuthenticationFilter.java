package banquemisr.challenge05.api.gateway.filter;

import banquemisr.challenge05.api.gateway.util.UserDetailsUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.context.MessageSource;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Locale;
import java.util.Objects;

@Component
@Slf4j
public class AuthenticationFilter implements GatewayFilter {

    @Resource(name = "messages_bundle")
    private final MessageSource messageSource;

    private final JwtDecoder jwtDecoder;

    public AuthenticationFilter(MessageSource messageSource, JwtDecoder jwtDecoder) {
        this.messageSource = messageSource;
        this.jwtDecoder = jwtDecoder;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String authToken = Objects.requireNonNull(exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION)).get(0);
        Jwt jwt = jwtDecoder.decode(authToken.substring("Bearer ".length()));

        String loggedInUserUsername = UserDetailsUtil.getUserName(jwt);
        String loggedInUserFullName = UserDetailsUtil.getUserFullName(jwt);
        String loggedInUserEmail = UserDetailsUtil.getEmail(jwt);
        Collection<GrantedAuthority> loggedInUserAuthorities = UserDetailsUtil.getGrantedAuthorities(jwt);
        String[] args = new String[4];
        args[0] = loggedInUserUsername;
        args[1] = loggedInUserFullName;
        args[2] = loggedInUserEmail;
        args[3] = loggedInUserAuthorities.toString();


        ServerHttpRequest request = exchange
                .getRequest()
                .mutate()
                .header("loggedInUserUsername", loggedInUserUsername)
                .header("loggedInUserFullName", loggedInUserFullName)
                .header("loggedInUserEmail", loggedInUserEmail)
                .header("loggedInUserAuthorities", String.valueOf(loggedInUserAuthorities))
                .build();

        log.info(messageSource.getMessage("APIGTWY000003", args, Locale.ENGLISH));

        return chain.filter(exchange.mutate().request(request).build());
    }
}
