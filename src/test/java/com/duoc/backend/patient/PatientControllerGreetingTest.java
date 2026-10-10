package com.duoc.backend.patient;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PatientControllerGreetingTest {

    @Test
    void shouldReturnGreetingWithProvidedName() {
        PatientController controller = new PatientController();

        assertEquals("Hello {Juan}", controller.greetings("Juan"));
    }

    @Test
    void shouldReturnGreetingWithWorld() {
        PatientController controller = new PatientController();

        assertEquals("Hello {World}", controller.greetings("World"));
    }
}
