package com.duoc.backend.Invoice;

import com.duoc.backend.care.Care;
import com.duoc.backend.care.CareRepository;
import com.duoc.backend.medication.Medication;
import com.duoc.backend.medication.MedicationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InvoiceServiceTest {

    @Mock
    private InvoiceRepository invoiceRepository;

    @Mock
    private MedicationRepository medicationRepository;

    @Mock
    private CareRepository careRepository;

    @InjectMocks
    private InvoiceService invoiceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldGetAllInvoices() {
        var invoices = Arrays.asList(
                new Invoice(1L, "Paciente 1", LocalDate.now(), Collections.emptyList(), Collections.emptyList()),
                new Invoice(2L, "Paciente 2", LocalDate.now(), Collections.emptyList(), Collections.emptyList())
        );
        when(invoiceRepository.findAll()).thenReturn(invoices);

        assertSame(invoices, invoiceService.getAllInvoices());
        verify(invoiceRepository).findAll();
    }

    @Test
    void shouldGetInvoiceById() {
        Invoice invoice = new Invoice(1L, "Paciente 1", LocalDate.now(),
                Collections.emptyList(), Collections.emptyList());
        when(invoiceRepository.findById(1L)).thenReturn(Optional.of(invoice));

        assertSame(invoice, invoiceService.getInvoiceById(1L));
    }

    @Test
    void shouldReturnNullWhenInvoiceDoesNotExist() {
        when(invoiceRepository.findById(99L)).thenReturn(Optional.empty());

        assertNull(invoiceService.getInvoiceById(99L));
    }

    @Test
    void shouldSaveInvoiceAndCalculateTotalCost() {
        Care care = new Care("Consulta", 50.0);
        care.setId(1L);

        Medication medication = new Medication("Antibiótico", 30.0);
        medication.setId(2L);

        Invoice invoice = new Invoice(1L, "Paciente 1", LocalDate.now(),
                Collections.singletonList(care), Collections.singletonList(medication));

        when(medicationRepository.findAllById(Collections.singletonList(2L)))
                .thenReturn(Collections.singletonList(medication));
        when(careRepository.findAllById(Collections.singletonList(1L)))
                .thenReturn(Collections.singletonList(care));
        when(invoiceRepository.save(invoice)).thenReturn(invoice);

        Invoice result = invoiceService.saveInvoice(invoice);

        assertSame(invoice, result);
        assertEquals(80.0, invoice.getTotalCost());
        verify(invoiceRepository).save(invoice);
    }

    @Test
    void shouldRejectInvoiceWhenMedicationDoesNotExist() {
        Medication medication = new Medication("Antibiótico", 30.0);
        medication.setId(2L);

        Invoice invoice = new Invoice(1L, "Paciente 1", LocalDate.now(),
                Collections.emptyList(), Collections.singletonList(medication));

        when(medicationRepository.findAllById(Collections.singletonList(2L)))
                .thenReturn(Collections.emptyList());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> invoiceService.saveInvoice(invoice)
        );

        assertEquals("Algunos medicamentos no existen en la base de datos.", exception.getMessage());
        verify(invoiceRepository, never()).save(any());
    }

    @Test
    void shouldRejectInvoiceWhenCareDoesNotExist() {
        Care care = new Care("Consulta", 50.0);
        care.setId(1L);

        Invoice invoice = new Invoice(1L, "Paciente 1", LocalDate.now(),
                Collections.singletonList(care), Collections.emptyList());

        when(medicationRepository.findAllById(Collections.emptyList()))
                .thenReturn(Collections.emptyList());
        when(careRepository.findAllById(Collections.singletonList(1L)))
                .thenReturn(Collections.emptyList());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> invoiceService.saveInvoice(invoice)
        );

        assertEquals("Algunos servicios no existen en la base de datos.", exception.getMessage());
        verify(invoiceRepository, never()).save(any());
    }

    @Test
    void shouldDeleteInvoice() {
        invoiceService.deleteInvoice(1L);

        verify(invoiceRepository).deleteById(1L);
    }
}
