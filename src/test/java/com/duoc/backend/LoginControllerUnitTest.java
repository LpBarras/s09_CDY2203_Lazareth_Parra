package com.duoc.backend;

import com.duoc.backend.user.MyUserDetailsService;
import com.duoc.backend.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoginControllerUnitTest {

    @Mock
    private JWTAuthenticationConfig jwtAuthenticationConfig;

    @Mock
    private MyUserDetailsService userDetailsService;

    @Mock
    private UserDetails userDetails;

    @InjectMocks
    private LoginController loginController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldLoginSuccessfully() {
        User request = new User();
        request.setUsername("prueba");
        request.setPassword("123456");

        when(userDetailsService.loadUserByUsername("prueba")).thenReturn(userDetails);
        when(userDetails.getPassword()).thenReturn("123456");
        when(jwtAuthenticationConfig.getJWTToken("prueba")).thenReturn("Bearer token");

        String result = loginController.login(request);

        assertEquals("Bearer token", result);
        verify(userDetailsService).loadUserByUsername("prueba");
        verify(jwtAuthenticationConfig).getJWTToken("prueba");
    }

    @Test
    void shouldRejectInvalidPassword() {
        User request = new User();
        request.setUsername("prueba");
        request.setPassword("incorrecta");

        when(userDetailsService.loadUserByUsername("prueba")).thenReturn(userDetails);
        when(userDetails.getPassword()).thenReturn("123456");

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> loginController.login(request)
        );

        assertEquals("Invalid login", exception.getMessage());
        verify(jwtAuthenticationConfig, never()).getJWTToken(anyString());
    }
}
