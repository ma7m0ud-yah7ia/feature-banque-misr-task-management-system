package banquemisr.challenge05.task.management.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserDTO {

    private String username;
    private String userFullName;
    private String email;
    private List<String> grantedAuthorities;

    public LoginUserDTO(String username){
        this.username = username;
    }
}
