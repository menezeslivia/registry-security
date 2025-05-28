package com.seguranca_urbana.backend.occurrenceServiceTest;

import com.seguranca_urbana.backend.models.dtos.occurrence.OccurrenceCategoryResponseDTO;
import com.seguranca_urbana.backend.models.dtos.occurrence.OccurrenceCategoryUpdateDTO;
import com.seguranca_urbana.backend.models.occurrence.OccurrenceCategory;
import com.seguranca_urbana.backend.repositorys.OccurrenceCategoryRepository;
import com.seguranca_urbana.backend.services.occurrence.UpdateCategoryService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateCategoryServiceTest {

    @Mock
    private OccurrenceCategoryRepository categoryRepository;

    @InjectMocks
    private UpdateCategoryService service;

    @Test
    void testExecute_Success() {
        Long id = 1L;
        OccurrenceCategoryUpdateDTO dto = new OccurrenceCategoryUpdateDTO("NOVA");
        OccurrenceCategory category = new OccurrenceCategory("VELHA");
        category.setId(id);

        when(categoryRepository.findById(id)).thenReturn(Optional.of(category));

        OccurrenceCategoryResponseDTO result = service.execute(id, dto);

        assertEquals(id, result.id());
        assertEquals("NOVA", result.name());
        verify(categoryRepository).save(category);
    }

    @Test
    void testExecute_NotFound_ThrowsException() {
        Long id = 2L;
        OccurrenceCategoryUpdateDTO dto = new OccurrenceCategoryUpdateDTO("TESTE");
        when(categoryRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.execute(id, dto));
        verify(categoryRepository, never()).save(any());
    }
}
