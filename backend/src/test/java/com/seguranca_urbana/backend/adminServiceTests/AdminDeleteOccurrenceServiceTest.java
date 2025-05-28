package com.seguranca_urbana.backend.adminServiceTests;

import com.seguranca_urbana.backend.repositorys.OccurrenceRepository;
import com.seguranca_urbana.backend.services.user.admin.AdminDeleteOccurrenceService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminDeleteOccurrenceServiceTest {

    @Mock
    private OccurrenceRepository occurrenceRepository;

    @InjectMocks
    private AdminDeleteOccurrenceService service;

    @Test
    void testExecute_Success() {
        Long id = 1L;
        when(occurrenceRepository.existsById(id)).thenReturn(true);

        service.execute(id);

        verify(occurrenceRepository).deleteById(id);
    }

    @Test
    void testExecute_NotFound_ThrowsException() {
        Long id = 1L;
        when(occurrenceRepository.existsById(id)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> service.execute(id));
        verify(occurrenceRepository, never()).deleteById(any());
    }
}