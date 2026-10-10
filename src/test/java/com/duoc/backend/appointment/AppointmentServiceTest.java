package com.duoc.backend.appointment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AppointmentServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @InjectMocks
    private AppointmentService appointmentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldGetAllAppointments() {
        List<Appointment> appointments = Arrays.asList(new Appointment(), new Appointment());
        when(appointmentRepository.findAll()).thenReturn(appointments);

        Iterable<Appointment> result = appointmentService.getAllAppointments();

        assertSame(appointments, result);
        verify(appointmentRepository).findAll();
    }

    @Test
    void shouldGetAppointmentById() {
        Appointment appointment = new Appointment();
        appointment.setId(1L);
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(appointment));

        Appointment result = appointmentService.getAppointmentById(1L);

        assertSame(appointment, result);
        verify(appointmentRepository).findById(1L);
    }

    @Test
    void shouldReturnNullWhenAppointmentDoesNotExist() {
        when(appointmentRepository.findById(99L)).thenReturn(Optional.empty());

        assertNull(appointmentService.getAppointmentById(99L));
    }

    @Test
    void shouldSaveAppointment() {
        Appointment appointment = new Appointment();
        when(appointmentRepository.save(appointment)).thenReturn(appointment);

        assertSame(appointment, appointmentService.saveAppointment(appointment));
        verify(appointmentRepository).save(appointment);
    }

    @Test
    void shouldDeleteAppointment() {
        appointmentService.deleteAppointment(1L);

        verify(appointmentRepository).deleteById(1L);
    }
}
