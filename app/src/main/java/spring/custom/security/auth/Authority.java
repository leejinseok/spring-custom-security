package spring.custom.security.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Authority {

    private String role;

    public static Authority create(String role) {
        Authority authority = new Authority();
        authority.role = role;
        return authority;
    }

}
