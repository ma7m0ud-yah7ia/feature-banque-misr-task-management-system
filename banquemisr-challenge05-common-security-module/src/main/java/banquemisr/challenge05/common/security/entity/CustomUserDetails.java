package banquemisr.challenge05.common.security.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {
    private String username;
    private String password;
    private String email;
    private Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(String username, Collection<? extends GrantedAuthority> authorities) {
        setUsername(username);
        setAuthorities(authorities);
    }
}
