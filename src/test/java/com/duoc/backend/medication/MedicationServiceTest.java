package com.duoc.backend.medication;

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

class MedicationServiceTest {

    @Mock
    private MedicationRepository medicationRepository;

    @InjectMocks
    private MedicationService medicationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldGetAllMedications() {
        List<Medication> medications = Arrays.asList(
                new Medication("Antibiótico", 30.0),
                new Medication("Analgésico", 20.0)
        );
        when(medicationRepository.findAll()).thenReturn(medications);

        assertSame(medications, medicationService.getAllMedications());
        verify(medicationRepository).findAll();
    }

    @Test
    void shouldGetMedicationById() {
        Medication medication = new Medication("Antibiótico", 30.0);
        medication.setId(1L);
        when(medicationRepository.findById(1L)).thenReturn(Optional.of(medication));

        assertSame(medication, medicationService.getMedicationById(1L));
        verify(medicationRepository).findById(1L);
    }

    @Test
    void shouldReturnNullWhenMedicationDoesNotExist() {
        when(medicationRepository.findById(99L)).thenReturn(Optional.empty());

        assertNull(medicationService.getMedicationById(99L));
    }

    @Test
    void shouldSaveMedication() {
        Medication medication = new Medication("Antibiótico", 30.0);
        when(medicationRepository.save(medication)).thenReturn(medication);

        assertSame(medication, medicationService.saveMedication(medication));
        verify(medicationRepository).save(medication);
    }

    @Test
    void shouldDeleteMedication() {
        medicationService.deleteMedication(1L);

        verify(medicationRepository).deleteById(1L);
    }
}
