package banquemisr.challenge05.common.security.service;

import banquemisr.challenge05.common.security.entity.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return  null;//new CustomUserDetails(username);
    }
}