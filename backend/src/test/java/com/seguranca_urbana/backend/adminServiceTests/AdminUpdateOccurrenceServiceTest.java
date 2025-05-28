package com.seguranca_urbana.backend.adminServiceTests;

import com.seguranca_urbana.backend.models.dtos.occurrence.OccurrenceResponseDTO;
import com.seguranca_urbana.backend.models.dtos.occurrence.OccurrenceUpdateDTO;
import com.seguranca_urbana.backend.models.occurrence.Occurrence;
import com.seguranca_urbana.backend.models.occurrence.OccurrenceCategory;
import com.seguranca_urbana.backend.repositorys.OccurrenceCategoryRepository;
import com.seguranca_urbana.backend.repositorys.OccurrenceRepository;
import com.seguranca_urbana.backend.services.mappers.OccurrenceDTOMapperService;
import com.seguranca_urbana.backend.services.user.admin.AdminUpdateOccurrenceService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminUpdateOccurrenceServiceTest {

    @Mock
    private OccurrenceRepository occurrenceRepository;
    @Mock
    private OccurrenceDTOMapperService occurrenceDTOMapperService;
    @Mock
    private OccurrenceCategoryRepository categoryRepository;

    @InjectMocks
    private AdminUpdateOccurrenceService service;

    @Test
    void testExecute_Success_WithCategoryUpdate() {
        Long id = 1L;
        Long categoryId = 2L;
        OccurrenceUpdateDTO dto = mock(OccurrenceUpdateDTO.class);
        when(dto.categoryId()).thenReturn(categoryId);

        Occurrence occurrence = mock(Occurrence.class);
        OccurrenceCategory category = new OccurrenceCategory();
        OccurrenceResponseDTO responseDTO = new OccurrenceResponseDTO(
                id, 3L, "Rua Teste", "desc", "ABERTA", "FURTO", null, LocalDateTime.now(), null
        );

        when(occurrenceRepository.findById(id)).thenReturn(Optional.of(occurrence));
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(occurrenceDTOMapperService.toDTO(occurrence)).thenReturn(responseDTO);

        OccurrenceResponseDTO result = service.execute(id, dto);

        assertSame(responseDTO, result);
        verify(occurrence).update(category, dto);
        verify(occurrenceRepository).save(occurrence);
    }

    @Test
    void testExecute_Success_WithoutCategoryUpdate() {
        Long id = 1L;
        OccurrenceUpdateDTO dto = mock(OccurrenceUpdateDTO.class);
        when(dto.categoryId()).thenReturn(null);

        Occurrence occurrence = mock(Occurrence.class);
        OccurrenceResponseDTO responseDTO = new OccurrenceResponseDTO(
                id, 3L, "Rua Teste", "desc", "ABERTA", "FURTO", null, LocalDateTime.now(), null
        );

        when(occurrenceRepository.findById(id)).thenReturn(Optional.of(occurrence));
        when(occurrenceDTOMapperService.toDTO(occurrence)).thenReturn(responseDTO);

        OccurrenceResponseDTO result = service.execute(id, dto);

        assertSame(responseDTO, result);
        verify(occurrence).update(null, dto);
        verify(occurrenceRepository).save(occurrence);
        verify(categoryRepository, never()).findById(any());
    }

    @Test
    void testExecute_OccurrenceNotFound_ThrowsException() {
        Long id = 1L;
        OccurrenceUpdateDTO dto = mock(OccurrenceUpdateDTO.class);
        when(occurrenceRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.execute(id, dto));
    }

    @Test
    void testExecute_CategoryNotFound_ThrowsException() {
        Long id = 1L;
        Long categoryId = 2L;
        OccurrenceUpdateDTO dto = mock(OccurrenceUpdateDTO.class);
        when(dto.categoryId()).thenReturn(categoryId);

        Occurrence occurrence = mock(Occurrence.class);

        when(occurrenceRepository.findById(id)).thenReturn(Optional.of(occurrence));
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.execute(id, dto));
    }
}
