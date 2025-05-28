package com.seguranca_urbana.backend.adminServiceTests;

import com.seguranca_urbana.backend.models.dtos.occurrence.OccurrenceResponseDTO;
import com.seguranca_urbana.backend.models.occurrence.Occurrence;
import com.seguranca_urbana.backend.repositorys.OccurrenceRepository;
import com.seguranca_urbana.backend.services.mappers.OccurrenceDTOMapperService;
import com.seguranca_urbana.backend.services.user.admin.AdminGetOccurrenceByIdService;
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
class AdminGetOccurrenceByIdServiceTest {

    @Mock
    private OccurrenceRepository occurrenceRepository;
    @Mock
    private OccurrenceDTOMapperService occurrenceDTOMapperService;

    @InjectMocks
    private AdminGetOccurrenceByIdService service;

    @Test
    void testExecute_OccurrenceFound_ReturnsDTO() {
        Long id = 1L;
        Occurrence occurrence = new Occurrence();
        OccurrenceResponseDTO dto = new OccurrenceResponseDTO(
                id, 2L, "Rua X", "desc", "ABERTA", "FURTO", null, LocalDateTime.now(), null
        );

        when(occurrenceRepository.findById(id)).thenReturn(Optional.of(occurrence));
        when(occurrenceDTOMapperService.toDTO(occurrence)).thenReturn(dto);

        OccurrenceResponseDTO result = service.execute(id);

        assertSame(dto, result);
    }

    @Test
    void testExecute_NotFound_ThrowsException() {
        Long id = 1L;
        when(occurrenceRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.execute(id));
    }
}
