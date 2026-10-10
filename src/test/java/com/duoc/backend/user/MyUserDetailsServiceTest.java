package com.duoc.backend.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MyUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private MyUserDetailsService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldLoadExistingUser() {
        User user = new User();
        user.setUsername("prueba");
        when(userRepository.findByUsername("prueba")).thenReturn(user);

        assertSame(user, service.loadUserByUsername("prueba"));
        verify(userRepository).findByUsername("prueba");
    }

    @Test
    void shouldThrowExceptionWhenUserDoesNotExist() {
        when(userRepository.findByUsername("inexistente")).thenReturn(null);

        assertThrows(
                UsernameNotFoundException.class,
                () -> service.loadUserByUsername("inexistente")
        );
    }

    @Test
    void shouldCreatePasswordEncoder() {
        PasswordEncoder encoder = service.passwordEncoder();

        assertNotNull(encoder);
        assertTrue(encoder.matches("123456", encoder.encode("123456")));
    }
}
