package com.duoc.backend;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JWTAuthenticationConfigTest {

    @Test
    void shouldGenerateBearerToken() {
        JWTAuthenticationConfig config = new JWTAuthenticationConfig();

        String token = config.getJWTToken("prueba");

        assertNotNull(token);
        assertTrue(token.startsWith(Constants.TOKEN_BEARER_PREFIX));
        assertTrue(token.length() > Constants.TOKEN_BEARER_PREFIX.length());
    }
}
