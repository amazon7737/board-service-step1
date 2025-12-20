package org.example.application.Auth;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;

    public UserService(UserRepository userRepository, AuthorityRepository authorityRepository) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
    }

    public LoginedUser login(Long id, String pw) {
        User user = userRepository.findByIdAndPassword(id, pw)
                .orElseThrow(() -> new org.example.application.Common.Exception.EntityNotFoundException("User", id));

        Authorities authority = authorityRepository.findByUserId(user.getId())
                .orElseThrow(() -> new org.example.application.Common.Exception.UnauthorizedAccessException("권한이 없는 사용자입니다."));

        return LoginedUser.of(user, authority);
    }

    public void signup(String username, String email, String password) {
        // TODO: Password encryption is needed
        userRepository.save(username, email, password);
    }
}
