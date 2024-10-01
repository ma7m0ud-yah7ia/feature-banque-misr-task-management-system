package banquemisr.challenge05.common.security.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AuthController {

    @GetMapping(value = "/get-principle")
    @PreAuthorize("hasAnyAuthority('admin-user')")
    public ResponseEntity<String> getPrinciple() {
        String data = "Principle is: "+ SecurityContextHolder.getContext().getAuthentication().getName()
                +"\t Authorities are: " + SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                +"\t isAuthenticated: " +SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}
