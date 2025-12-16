package org.example.application;

import org.example.application.Auth.*;
import org.example.application.Auth.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@DisplayName("Story: User Authentication")
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthorityRepository authorityRepository;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("Scenario: Login successful with valid credentials and authority")
    void loginSuccess() {
        // Given
        Long userId = 1L;
        String password = "password123";
        User user = new User(userId, password, "User1", "user@example.com", LocalDateTime.now());
        Authorities auth = new Authorities(10L, "USER");

        given(userRepository.findByIdAndPassword(userId, password)).willReturn(Optional.of(user));
        given(authorityRepository.findByUserId(userId)).willReturn(Optional.of(auth));

        // When
        LoginedUser result = userService.login(userId, password);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.userId()).isEqualTo(userId);
        assertThat(result.role()).isEqualTo("USER");
    }

    @Test
    @DisplayName("Scenario: Login fails when user not found")
    void loginFailUserNotFound() {
        // Given
        Long userId = 999L;
        String password = "wrongpassword";
        given(userRepository.findByIdAndPassword(userId, password)).willReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> userService.login(userId, password))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("존재하지 않는 사용자입니다.");
    }
    
    @Test
    @DisplayName("Scenario: Login fails when user has no authority")
    void loginFailNoAuthority() {
        // Given
        Long userId = 1L;
        String password = "password123";
        User user = new User(userId, password, "User1", "user@example.com", LocalDateTime.now());
        
        given(userRepository.findByIdAndPassword(userId, password)).willReturn(Optional.of(user));
        given(authorityRepository.findByUserId(userId)).willReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> userService.login(userId, password))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("권한이 없는 사용자입니다.");
    }
}
