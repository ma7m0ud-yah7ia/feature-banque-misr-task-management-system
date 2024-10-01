package banquemisr.challenge05.common.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonPrincipalDTO {
    private String username;
    private Collection<? extends GrantedAuthority> authorities;
}
