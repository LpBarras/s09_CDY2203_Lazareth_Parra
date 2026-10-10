package com.duoc.backend.care;

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

class CareServiceTest {

    @Mock
    private CareRepository careRepository;

    @InjectMocks
    private CareService careService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldGetAllCares() {
        List<Care> cares = Arrays.asList(new Care("Consulta", 50.0), new Care("Vacuna", 30.0));
        when(careRepository.findAll()).thenReturn(cares);

        assertSame(cares, careService.getAllCares());
        verify(careRepository).findAll();
    }

    @Test
    void shouldGetCareById() {
        Care care = new Care("Consulta", 50.0);
        care.setId(1L);
        when(careRepository.findById(1L)).thenReturn(Optional.of(care));

        assertSame(care, careService.getCareById(1L));
        verify(careRepository).findById(1L);
    }

    @Test
    void shouldReturnNullWhenCareDoesNotExist() {
        when(careRepository.findById(99L)).thenReturn(Optional.empty());

        assertNull(careService.getCareById(99L));
    }

    @Test
    void shouldSaveCare() {
        Care care = new Care("Consulta", 50.0);
        when(careRepository.save(care)).thenReturn(care);

        assertSame(care, careService.saveCare(care));
        verify(careRepository).save(care);
    }

    @Test
    void shouldDeleteCare() {
        careService.deleteCare(1L);

        verify(careRepository).deleteById(1L);
    }
}
