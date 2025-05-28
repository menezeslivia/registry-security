package com.seguranca_urbana.backend.commonerServiceTests;

import com.seguranca_urbana.backend.models.dtos.occurrence.OccurrenceResponseDTO;
import com.seguranca_urbana.backend.models.occurrence.Occurrence;
import com.seguranca_urbana.backend.repositorys.OccurrenceRepository;
import com.seguranca_urbana.backend.services.mappers.OccurrenceDTOMapperService;
import com.seguranca_urbana.backend.services.user.commoner.GetOwnOccurrencesService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetOwnOccurrencesServiceTest {

    @Mock
    private OccurrenceRepository occurrenceRepository;
    @Mock
    private OccurrenceDTOMapperService occurrenceDTOMapperService;

    @InjectMocks
    private GetOwnOccurrencesService service;

    @Test
    void testExecute_ReturnsListOfResponses() {
        Long userId = 1L;
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

        when(occurrenceRepository.findAllByUserId(userId)).thenReturn(List.of(occurrence));
        when(occurrenceDTOMapperService.toDTO(occurrence)).thenReturn(dto);

        List<OccurrenceResponseDTO> result = service.execute(userId);

        assertEquals(1, result.size());
        assertSame(dto, result.get(0));
        verify(occurrenceRepository).findAllByUserId(userId);
        verify(occurrenceDTOMapperService).toDTO(occurrence);
    }
}