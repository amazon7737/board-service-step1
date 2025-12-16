package org.example.application.Auth;

import java.io.Serializable;

public record LoginedUser(
        Long userId,
        String role
) implements Serializable {
    public static LoginedUser of(User user, Authorities authority) {
        return new LoginedUser(user.getId(), authority.getName());
    }
}
