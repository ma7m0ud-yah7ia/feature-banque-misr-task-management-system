package banquemisr.challenge05.api.gateway.security;

import banquemisr.challenge05.api.gateway.dto.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Locale;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomServerAuthenticationAccessDeniedHandler implements ServerAccessDeniedHandler {


    private final ObjectMapper objectMapper = new ObjectMapper();

    @Resource(name = "messages_bundle")
    private final MessageSource messageSource;


    @Override
    public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException e) {
        ErrorResponse errorResponse = null;
        if (e != null) {
            errorResponse = new ErrorResponse(
                    HttpStatus.FORBIDDEN,
                    HttpStatus.FORBIDDEN.value(),
                    messageSource.getMessage("APIGTWY000005", null, Locale.ENGLISH),
                    messageSource.getMessage("APIGTWY000006", null, Locale.ENGLISH));
            log.error("Access exception {}", errorResponse.getError().getErrorDetails());
        }

        return writeResponse(exchange, errorResponse);

    }

    private Mono<Void> writeResponse(ServerWebExchange exchange, ErrorResponse errorResponse) {
        try {
            // Serialize the response body to JSON
            byte[] bytes = objectMapper.writeValueAsBytes(errorResponse);

            // Prepare response
            exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
            exchange.getResponse().getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

            // Write the response body
            DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
            return exchange.getResponse().writeWith(Mono.just(buffer));
        } catch (Exception ex) {
            return Mono.error(ex);
        }
    }
}
