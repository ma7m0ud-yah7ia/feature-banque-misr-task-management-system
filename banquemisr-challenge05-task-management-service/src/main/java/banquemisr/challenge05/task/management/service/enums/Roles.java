package banquemisr.challenge05.task.management.service.enums;

import lombok.Getter;

@Getter
public enum Roles {
    ADMIN("admin-user"), REGULAR("regular-user");

    final String value;

    Roles(String value) {
        this.value = value;
    }
}
