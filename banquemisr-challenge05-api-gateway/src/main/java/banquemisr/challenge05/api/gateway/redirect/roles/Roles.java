package banquemisr.challenge05.api.gateway.redirect.roles;

import lombok.Getter;

@Getter
public enum Roles {

    ADMIN_ROLES("admin-user"),

    USER_ROLES("regular-user");

    private final String value;

    Roles(String roleValue) {
        this.value = roleValue;
    }
}
