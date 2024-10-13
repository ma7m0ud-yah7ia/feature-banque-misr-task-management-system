package banquemisr.challenge05.task.management.service.service;

import java.util.List;

public interface UserService {
    void setLoginUser(String username, String userFullName, String email, List<String> loggedInUserAuthorities);
}
