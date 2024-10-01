package banquemisr.challenge05.task.management.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUserDTO {
    private String userName;
    private String email;
    private Set<String> authorities;
}
