package com.seguranca_urbana.backend.occurrenceServiceTest;

import com.seguranca_urbana.backend.models.dtos.occurrence.OccurrenceCategoryRequestDTO;
import com.seguranca_urbana.backend.models.dtos.occurrence.OccurrenceCategoryResponseDTO;
import com.seguranca_urbana.backend.models.occurrence.OccurrenceCategory;
import com.seguranca_urbana.backend.repositorys.OccurrenceCategoryRepository;
import com.seguranca_urbana.backend.services.occurrence.CreateCategoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateCategoryServiceTest {

    @Mock
    private OccurrenceCategoryRepository categoryRepository;

    @InjectMocks
    private CreateCategoryService service;

    @Test
    void testExecute_Success() {
        OccurrenceCategoryRequestDTO dto = new OccurrenceCategoryRequestDTO("FURTO");
        when(categoryRepository.existsByName("FURTO")).thenReturn(false);

        // Simule o comportamento do save
        OccurrenceCategory category = new OccurrenceCategory("FURTO");
        category.setId(1L);
        doAnswer(invocation -> {
            OccurrenceCategory cat = invocation.getArgument(0);
            cat.setId(1L);
            return null;
        }).when(categoryRepository).save(any(OccurrenceCategory.class));

        OccurrenceCategoryResponseDTO result = service.execute(dto);

        assertEquals(1L, result.id());
        assertEquals("FURTO", result.name());
        verify(categoryRepository).save(any(OccurrenceCategory.class));
    }

    @Test
    void testExecute_CategoryExists_ThrowsException() {
        OccurrenceCategoryRequestDTO dto = new OccurrenceCategoryRequestDTO("FURTO");
        when(categoryRepository.existsByName("FURTO")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> service.execute(dto));
        verify(categoryRepository, never()).save(any());
    }
}
