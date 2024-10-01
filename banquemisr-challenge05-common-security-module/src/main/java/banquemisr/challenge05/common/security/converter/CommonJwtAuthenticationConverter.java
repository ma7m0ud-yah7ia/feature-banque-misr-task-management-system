package banquemisr.challenge05.common.security.converter;

import banquemisr.challenge05.common.security.dto.AppUserDTO;
import banquemisr.challenge05.common.security.entity.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CommonJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {

        AbstractAuthenticationToken auth = new JwtAuthenticationToken(jwt, getAuthorities(jwt));

        return createJwtAuthenticationToken(jwt, auth);
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Jwt jwt) {
        return getAppUser(jwt).getGrantedAuthorities();
    }

    private JwtAuthenticationToken createJwtAuthenticationToken(Jwt jwt, AbstractAuthenticationToken auth) {
        String username = jwt.getClaimAsString("preferred_username"); // Extract username or custom field
        Collection<? extends GrantedAuthority> authorities;
        authorities = auth.getAuthorities(); // Extract authorities

        // Create custom principal with claims
        CustomUserDetails customUserDetails = new CustomUserDetails(username, authorities);
        setAuthentication(customUserDetails);

        // Create a JwtAuthenticationToken with the custom principal
        return new JwtAuthenticationToken(jwt, authorities, customUserDetails.getUsername());
    }


    public void setAuthentication(CustomUserDetails commonPrincipalDTO) {
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(commonPrincipalDTO, null, commonPrincipalDTO.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public AppUserDTO getAppUser(Jwt jwt) {

        AppUserDTO appUserDTO = new AppUserDTO();
        appUserDTO.setUserName(jwt.getClaim("preferred_username"));
        appUserDTO.setEmail(jwt.getClaim("email"));

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        if (jwt.getClaim("realm_access") != null) {
            Map<String, Object> realmAccess = jwt.getClaim("realm_access");
            if (realmAccess.get("roles") != null) {
                Collection<String> roles = (Collection<String>) realmAccess.get("roles");
                grantedAuthorities =  roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
            }
        }
        appUserDTO.setGrantedAuthorities(grantedAuthorities);
        return appUserDTO;
    }
}
