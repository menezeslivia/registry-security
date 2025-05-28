package com.seguranca_urbana.backend.occurrenceServiceTest;

import com.seguranca_urbana.backend.models.dtos.occurrence.OccurrenceCategoryResponseDTO;
import com.seguranca_urbana.backend.models.occurrence.OccurrenceCategory;
import com.seguranca_urbana.backend.repositorys.OccurrenceCategoryRepository;
import com.seguranca_urbana.backend.services.occurrence.GetAllCategoriesService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetAllCategoriesServiceTest {

    @Mock
    private OccurrenceCategoryRepository categoryRepository;

    @InjectMocks
    private GetAllCategoriesService service;

    @Test
    void testExecute_ReturnsListOfDTOs() {
        OccurrenceCategory cat1 = new OccurrenceCategory("FURTO");
        cat1.setId(1L);
        OccurrenceCategory cat2 = new OccurrenceCategory("ROUBO");
        cat2.setId(2L);

        when(categoryRepository.findAll()).thenReturn(List.of(cat1, cat2));

        List<OccurrenceCategoryResponseDTO> result = service.execute();

        assertEquals(2, result.size());
        assertEquals("FURTO", result.get(0).name());
        assertEquals("ROUBO", result.get(1).name());
    }
}
