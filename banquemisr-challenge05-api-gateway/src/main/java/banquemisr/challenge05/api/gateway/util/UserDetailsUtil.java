package banquemisr.challenge05.api.gateway.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Setter
@Getter
public class UserDetailsUtil {
    
    public static String getEmail(Jwt jwt) {
        return jwt.getClaim("email");
    }

    public static String getUserFullName(Jwt jwt) {
        return jwt.getClaim("name");
    }

    public static String getUserName(Jwt jwt) {
        return jwt.getClaim("preferred_username");
    }

    public static Collection<GrantedAuthority> getGrantedAuthorities(Jwt jwt) {
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        if (jwt.getClaim("realm_access") != null) {
            Map<String, Object> realmAccess = jwt.getClaim("realm_access");
            if (realmAccess.get("roles") != null) {
                Collection<String> roles = (Collection<String>) realmAccess.get("roles");
                grantedAuthorities = roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
            }
        }
        return grantedAuthorities;
    }


}
