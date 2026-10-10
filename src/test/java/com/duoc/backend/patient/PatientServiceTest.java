package com.duoc.backend.patient;

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

class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientService patientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldGetAllPatients() {
        List<Patient> patients = Arrays.asList(new Patient(), new Patient());
        when(patientRepository.findAll()).thenReturn(patients);

        assertSame(patients, patientService.getAllPatients());
        verify(patientRepository).findAll();
    }

    @Test
    void shouldGetPatientById() {
        Patient patient = new Patient();
        patient.setId(1L);
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));

        assertSame(patient, patientService.getPatientById(1L));
        verify(patientRepository).findById(1L);
    }

    @Test
    void shouldReturnNullWhenPatientDoesNotExist() {
        when(patientRepository.findById(99L)).thenReturn(Optional.empty());

        assertNull(patientService.getPatientById(99L));
    }

    @Test
    void shouldSavePatient() {
        Patient patient = new Patient();
        when(patientRepository.save(patient)).thenReturn(patient);

        assertSame(patient, patientService.savePatient(patient));
        verify(patientRepository).save(patient);
    }

    @Test
    void shouldDeletePatient() {
        patientService.deletePatient(1L);

        verify(patientRepository).deleteById(1L);
    }
}
