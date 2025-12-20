package org.example.application.Auth;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    void save(String username, String email, String password);
    User findById(Long id);
    Optional<User> findByEmail(String email);
    Optional<User> findByIdAndPassword(Long id, String pw);
    List<User> findAll();
    void updateUsername(Long id, String username);
    void deleteById(Long id);
}
