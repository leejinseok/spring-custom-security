package spring.custom.security.user;


import lombok.Getter;
import lombok.Setter;
import spring.custom.security.auth.Authority;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UserDto {

    private String username;
    private final List<Authority> roles = new ArrayList<>();

    public void addRole(Authority role) {
        this.roles.add(role);
    }

}
