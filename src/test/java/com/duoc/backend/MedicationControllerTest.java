package com.duoc.backend;

import com.duoc.backend.medication.Medication;
import com.duoc.backend.medication.MedicationController;
import com.duoc.backend.medication.MedicationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MedicationControllerTest {

    @Mock
    private MedicationService medicationService;

    @InjectMocks
    private MedicationController medicationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldGetAllMedications() {
        when(medicationService.getAllMedications()).thenReturn(
                Arrays.asList(new Medication("Antibiótico", 30.0), new Medication("Analgésico", 20.0))
        );

        assertEquals(2, medicationController.getAllMedications().size());
        verify(medicationService).getAllMedications();
    }

    @Test
    void shouldGetMedicationById() {
        Medication medication = new Medication("Antibiótico", 30.0);
        when(medicationService.getMedicationById(1L)).thenReturn(medication);

        assertSame(medication, medicationController.getMedicationById(1L));
    }

    @Test
    void shouldSaveMedication() {
        Medication medication = new Medication("Antibiótico", 30.0);
        when(medicationService.saveMedication(medication)).thenReturn(medication);

        assertSame(medication, medicationController.saveMedication(medication));
        verify(medicationService).saveMedication(medication);
    }

    @Test
    void shouldDeleteMedication() {
        medicationController.deleteMedication(1L);

        verify(medicationService).deleteMedication(1L);
    }
}
