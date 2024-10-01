package banquemisr.challenge05.common.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUserDTO {
    private String userName;
    private String email;
    private Collection<GrantedAuthority> grantedAuthorities;
}
