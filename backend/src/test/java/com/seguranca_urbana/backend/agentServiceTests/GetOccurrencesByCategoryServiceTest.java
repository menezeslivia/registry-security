package com.seguranca_urbana.backend.agentServiceTests;

import com.seguranca_urbana.backend.models.dtos.occurrence.OccurrenceResponseDTO;
import com.seguranca_urbana.backend.models.occurrence.Occurrence;
import com.seguranca_urbana.backend.models.occurrence.OccurrenceCategory;
import com.seguranca_urbana.backend.repositorys.OccurrenceCategoryRepository;
import com.seguranca_urbana.backend.repositorys.OccurrenceRepository;
import com.seguranca_urbana.backend.services.mappers.OccurrenceDTOMapperService;
import com.seguranca_urbana.backend.services.user.agent.GetOccurrencesByCategoryService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetOccurrencesByCategoryServiceTest {

    @Mock
    private OccurrenceRepository occurrenceRepository;
    @Mock
    private OccurrenceCategoryRepository occurrenceCategoryRepository;
    @Mock
    private OccurrenceDTOMapperService occurrenceDTOMapperService;

    @InjectMocks
    private GetOccurrencesByCategoryService service;

    @Test
    void testExecute_ReturnsListOfDTOs() {
        String categoryName = "FURTO";
        OccurrenceCategory category = new OccurrenceCategory();
        category.setName(categoryName);

        Occurrence occurrence = new Occurrence();
        OccurrenceResponseDTO dto = new OccurrenceResponseDTO(
                1L, 2L, "Rua B", "desc", "ABERTA", categoryName, null, LocalDateTime.now(), null
        );

        when(occurrenceCategoryRepository.findByName(categoryName)).thenReturn(Optional.of(category));
        when(occurrenceRepository.findByCategory(category)).thenReturn(List.of(occurrence));
        when(occurrenceDTOMapperService.toDTO(occurrence)).thenReturn(dto);

        List<OccurrenceResponseDTO> result = service.execute(categoryName);

        assertEquals(1, result.size());
        assertSame(dto, result.get(0));
    }

    @Test
    void testExecute_CategoryNotFound_ThrowsException() {
        String categoryName = "NAOEXISTE";
        when(occurrenceCategoryRepository.findByName(categoryName)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.execute(categoryName));
    }
}
