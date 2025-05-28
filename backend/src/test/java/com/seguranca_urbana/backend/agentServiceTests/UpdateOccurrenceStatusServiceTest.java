package com.seguranca_urbana.backend.agentServiceTests;

import com.seguranca_urbana.backend.models.dtos.occurrence.OccurrenceResponseDTO;
import com.seguranca_urbana.backend.models.dtos.occurrence.OccurrenceUpdateDTO;
import com.seguranca_urbana.backend.models.enums.OccurrenceStatus;
import com.seguranca_urbana.backend.models.occurrence.Occurrence;
import com.seguranca_urbana.backend.repositorys.OccurrenceRepository;
import com.seguranca_urbana.backend.services.mappers.OccurrenceDTOMapperService;
import com.seguranca_urbana.backend.services.user.agent.UpdateOccurrenceStatusService;
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
class UpdateOccurrenceStatusServiceTest {

    @Mock
    private OccurrenceRepository occurrenceRepository;
    @Mock
    private OccurrenceDTOMapperService occurrenceDTOMapperService;

    @InjectMocks
    private UpdateOccurrenceStatusService service;

    @Test
    void testExecute_UpdatesStatusAndReturnsDTO() {
        Long id = 1L;
        OccurrenceStatus newStatus = OccurrenceStatus.RESOLVED;
        OccurrenceUpdateDTO dto = mock(OccurrenceUpdateDTO.class);
        when(dto.status()).thenReturn(newStatus);

        Occurrence occurrence = new Occurrence();
        when(occurrenceRepository.findById(id)).thenReturn(Optional.of(occurrence));

        OccurrenceResponseDTO responseDTO = new OccurrenceResponseDTO(
                1L, 1L, "Rua A", "desc", newStatus.name(), "FURTO", null, LocalDateTime.now(), null
        );
        when(occurrenceDTOMapperService.toDTO(occurrence)).thenReturn(responseDTO);

        OccurrenceResponseDTO result = service.execute(id, dto);

        assertSame(responseDTO, result);
        assertEquals(newStatus, occurrence.getStatus());
        verify(occurrenceRepository).save(occurrence);
    }

    @Test
    void testExecute_OccurrenceNotFound_ThrowsException() {
        Long id = 1L;
        OccurrenceUpdateDTO dto = mock(OccurrenceUpdateDTO.class);
        when(occurrenceRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.execute(id, dto));
    }
}