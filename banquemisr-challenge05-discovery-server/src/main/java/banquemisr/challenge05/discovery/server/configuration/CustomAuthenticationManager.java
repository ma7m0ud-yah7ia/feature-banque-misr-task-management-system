package banquemisr.challenge05.discovery.server.configuration;

import banquemisr.challenge05.discovery.server.service.EncryptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationManager implements AuthenticationManager {

    @Value("${spring.eureka.security.user.name}")
    private String eurekaUsername;

    @Value("${spring.eureka.security.user.password}")
    private String eurekaPassword;

    @Value("${spring.eureka.security.user.role}")
    private String eurekaUserRole;

    private final EncryptionService encryptionService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserDetails userDetails = User.builder()
                .username(username)
                .password(encryptionService.decrypt(password))
                .roles(eurekaUserRole).build();

        if (!eurekaUsername.equals(userDetails.getUsername()) && !eurekaPassword.equals(userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }

        return new UsernamePasswordAuthenticationToken(username, password, userDetails.getAuthorities());
    }
}
