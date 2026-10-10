package com.duoc.backend;

import com.duoc.backend.appointment.Appointment;
import com.duoc.backend.appointment.AppointmentController;
import com.duoc.backend.appointment.AppointmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AppointmentControllerTest {

    @Mock
    private AppointmentService appointmentService;

    @InjectMocks
    private AppointmentController appointmentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldGetAllAppointments() {
        Appointment a1 = new Appointment();
        a1.setId(1L);
        Appointment a2 = new Appointment();
        a2.setId(2L);
        when(appointmentService.getAllAppointments()).thenReturn(Arrays.asList(a1, a2));

        List<Appointment> result = appointmentController.getAllAppointments();

        assertEquals(2, result.size());
        verify(appointmentService).getAllAppointments();
    }

    @Test
    void shouldGetAppointmentById() {
        Appointment appointment = new Appointment();
        appointment.setId(1L);
        when(appointmentService.getAppointmentById(1L)).thenReturn(appointment);

        assertSame(appointment, appointmentController.getAppointmentById(1L));
    }

    @Test
    void shouldSaveAppointment() {
        Appointment appointment = new Appointment();
        appointment.setDate(LocalDate.of(2025, 4, 28));
        appointment.setTime(LocalTime.of(10, 30));
        when(appointmentService.saveAppointment(appointment)).thenReturn(appointment);

        assertSame(appointment, appointmentController.saveAppointment(appointment));
        verify(appointmentService).saveAppointment(appointment);
    }

    @Test
    void shouldDeleteAppointment() {
        appointmentController.deleteAppointment(1L);

        verify(appointmentService).deleteAppointment(1L);
    }
}
