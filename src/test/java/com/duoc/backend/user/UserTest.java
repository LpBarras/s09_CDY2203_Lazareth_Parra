package com.duoc.backend.user;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void shouldSetAndGetUserData() {
        User user = new User();

        user.setId(1);
        user.setUsername("prueba");
        user.setEmail("prueba@test.cl");
        user.setPassword("123456");
        user.setEnabled(true);

        assertEquals(1, user.getId());
        assertEquals("prueba", user.getUsername());
        assertEquals("prueba@test.cl", user.getEmail());
        assertEquals("123456", user.getPassword());
        assertTrue(user.isEnabled());
    }

    @Test
    void shouldReturnDefaultAccountStatusValues() {
        User user = new User();

        assertTrue(user.isAccountNonExpired());
        assertTrue(user.isAccountNonLocked());
        assertTrue(user.isCredentialsNonExpired());
        assertNull(user.getAuthorities());
    }
}
