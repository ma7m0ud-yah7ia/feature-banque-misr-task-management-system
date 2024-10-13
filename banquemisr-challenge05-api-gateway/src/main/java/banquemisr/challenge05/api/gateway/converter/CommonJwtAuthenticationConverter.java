package banquemisr.challenge05.api.gateway.converter;

import banquemisr.challenge05.api.gateway.util.UserDetailsUtil;
import jakarta.annotation.Resource;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
@Data
@Slf4j
@RequiredArgsConstructor
public class CommonJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    @Resource(name = "messages_bundle")
    private final MessageSource messageSource;

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) throws RuntimeException {
        return new JwtAuthenticationToken(jwt, UserDetailsUtil.getGrantedAuthorities(jwt), UserDetailsUtil.getUserName(jwt));
    }
}
