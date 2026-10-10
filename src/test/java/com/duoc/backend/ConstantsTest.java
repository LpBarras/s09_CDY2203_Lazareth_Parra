package com.duoc.backend;

import org.junit.jupiter.api.Test;

import java.security.Key;

import static org.junit.jupiter.api.Assertions.*;

class ConstantsTest {

    @Test
    void shouldCreateSigningKeyFromBase64Secret() {
        Key key = Constants.getSigningKeyB64(Constants.SUPER_SECRET_KEY);

        assertNotNull(key);
        assertEquals("HmacSHA512", key.getAlgorithm());
    }

    @Test
    void shouldCreateSigningKeyFromPlainSecret() {
        Key key = Constants.getSigningKey(Constants.SUPER_SECRET_KEY);

        assertNotNull(key);
        assertEquals("HmacSHA512", key.getAlgorithm());
    }
}
