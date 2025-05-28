package com.seguranca_urbana.backend.occurrenceServiceTest;

import com.seguranca_urbana.backend.models.dtos.occurrence.OccurrenceCategoryResponseDTO;
import com.seguranca_urbana.backend.models.occurrence.OccurrenceCategory;
import com.seguranca_urbana.backend.repositorys.OccurrenceCategoryRepository;
import com.seguranca_urbana.backend.services.occurrence.GetCategoryByIdService;
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
class GetCategoryByIdServiceTest {

    @Mock
    private OccurrenceCategoryRepository categoryRepository;

    @InjectMocks
    private GetCategoryByIdService service;

    @Test
    void testExecute_Found_ReturnsDTO() {
        Long id = 1L;
        OccurrenceCategory category = new OccurrenceCategory("EXEMPLO");
        category.setId(id);

        when(categoryRepository.findById(id)).thenReturn(Optional.of(category));

        OccurrenceCategoryResponseDTO result = service.execute(id);

        assertEquals(id, result.id());
        assertEquals("EXEMPLO", result.name());
    }

    @Test
    void testExecute_NotFound_ThrowsException() {
        Long id = 2L;
        when(categoryRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.execute(id));
    }
}
