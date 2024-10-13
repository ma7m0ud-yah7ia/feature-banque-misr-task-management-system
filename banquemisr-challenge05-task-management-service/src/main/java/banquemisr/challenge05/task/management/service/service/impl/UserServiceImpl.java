package banquemisr.challenge05.task.management.service.service.impl;

import banquemisr.challenge05.task.management.service.model.LoginUser;
import banquemisr.challenge05.task.management.service.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;

@Setter
@Getter
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private LoginUser loginUser;

    @Override
    public void setLoginUser(String username, String userFullName, String email, List<String> loggedInUserAuthorities) {
        loginUser = new LoginUser(username, userFullName, email, loggedInUserAuthorities);
    }

}
