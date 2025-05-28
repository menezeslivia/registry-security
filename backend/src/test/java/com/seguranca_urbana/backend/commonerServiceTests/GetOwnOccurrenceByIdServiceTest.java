package com.seguranca_urbana.backend.commonerServiceTests;

import com.seguranca_urbana.backend.models.dtos.occurrence.OccurrenceResponseDTO;
import com.seguranca_urbana.backend.models.occurrence.Occurrence;
import com.seguranca_urbana.backend.repositorys.OccurrenceRepository;
import com.seguranca_urbana.backend.services.mappers.OccurrenceDTOMapperService;
import com.seguranca_urbana.backend.services.user.commoner.GetOwnOccurrenceByIdService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetOwnOccurrenceByIdServiceTest {

    @Mock
    private OccurrenceRepository occurrenceRepository;
    @Mock
    private OccurrenceDTOMapperService occurrenceDTOMapperService;

    @InjectMocks
    private GetOwnOccurrenceByIdService service;

    @Test
    void testExecute_ReturnsResponse() {
        Long userId = 1L, occurrenceId = 2L;
        Occurrence occurrence = new Occurrence();
        OccurrenceResponseDTO dto = new OccurrenceResponseDTO(
                1L,    // id
                1L,    // userId
                "Rua A", // address
                "desc",  // description
                "ABERTA", // status
                "FURTO",  // category
                null,     // photo
                LocalDateTime.now(), // createdAt
                null      // agentId
        );

        when(occurrenceRepository.findByIdAndUserId(occurrenceId, userId)).thenReturn(occurrence);
        when(occurrenceDTOMapperService.toDTO(occurrence)).thenReturn(dto);

        OccurrenceResponseDTO result = service.execute(userId, occurrenceId);

        assertSame(dto, result);
        verify(occurrenceRepository).findByIdAndUserId(occurrenceId, userId);
        verify(occurrenceDTOMapperService).toDTO(occurrence);
    }

    @Test
    void testExecute_NotFound_ThrowsException() {
        Long userId = 1L, occurrenceId = 2L;
        when(occurrenceRepository.findByIdAndUserId(occurrenceId, userId)).thenReturn(null);

        assertThrows(EntityNotFoundException.class,
                () -> service.execute(userId, occurrenceId));
    }
}