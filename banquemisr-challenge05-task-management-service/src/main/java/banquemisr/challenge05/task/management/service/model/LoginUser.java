package banquemisr.challenge05.task.management.service.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class LoginUser {

    private String username;
    private String userFullName;
    private String email;
    private List<String> grantedAuthorities;

    public LoginUser(String username){
        this.username = username;
    }

    public LoginUser(String username, String userFullName, String email, List<String> grantedAuthorities) {
        this.username = username;
        this.userFullName = userFullName;
        this.email = email;
        this.grantedAuthorities = grantedAuthorities;
    }
}
