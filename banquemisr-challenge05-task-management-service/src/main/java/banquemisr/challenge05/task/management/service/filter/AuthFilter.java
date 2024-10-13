package banquemisr.challenge05.task.management.service.filter;

import banquemisr.challenge05.task.management.service.service.impl.UserServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthFilter extends OncePerRequestFilter {

    private final UserServiceImpl userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (request.getHeader("loggedInUserUsername") != null) {
            String loggedInUserUsername = request.getHeader("loggedInUserUsername");
            String loggedInUserFullName = request.getHeader("loggedInUserFullName");
            String loggedInUserEmail = request.getHeader("loggedInUserEmail");
            List<String> loggedInUserAuthorities = List.of(request.getHeader("loggedInUserAuthorities").split(","));

            userService.setLoginUser(loggedInUserUsername, loggedInUserFullName, loggedInUserEmail, loggedInUserAuthorities);
            log.info("Logging request data: loggedInUserUsername: {},loggedInUserFullName: {}, loggedInUserEmail: {}, loggedInUserAuthorities: {}",
                    loggedInUserUsername, loggedInUserFullName, loggedInUserEmail, loggedInUserAuthorities);
        }
        filterChain.doFilter(request, response);
    }
}
