package com.seguranca_urbana.backend.occurrenceServiceTest;

import com.seguranca_urbana.backend.repositorys.OccurrenceCategoryRepository;
import com.seguranca_urbana.backend.services.occurrence.DeleteCategoryService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteCategoryServiceTest {

    @Mock
    private OccurrenceCategoryRepository categoryRepository;

    @InjectMocks
    private DeleteCategoryService service;

    @Test
    void testExecute_Success() {
        Long id = 1L;
        when(categoryRepository.existsById(id)).thenReturn(true);

        service.execute(id);

        verify(categoryRepository).deleteById(id);
    }

    @Test
    void testExecute_NotFound_ThrowsException() {
        Long id = 2L;
        when(categoryRepository.existsById(id)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> service.execute(id));
        verify(categoryRepository, never()).deleteById(any());
    }
}
