package com.seguranca_urbana.backend.adminServiceTests;

import com.seguranca_urbana.backend.models.dtos.occurrence.OccurrenceResponseDTO;
import com.seguranca_urbana.backend.models.occurrence.Occurrence;
import com.seguranca_urbana.backend.repositorys.OccurrenceRepository;
import com.seguranca_urbana.backend.services.mappers.OccurrenceDTOMapperService;
import com.seguranca_urbana.backend.services.user.admin.AdminGetAllOccurrencesService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminGetAllOccurrencesServiceTest {

    @Mock
    private OccurrenceRepository occurrenceRepository;
    @Mock
    private OccurrenceDTOMapperService occurrenceDTOMapperService;

    @InjectMocks
    private AdminGetAllOccurrencesService service;

    @Test
    void testExecute_ReturnsAllDTOs() {
        Occurrence occurrence = new Occurrence();
        OccurrenceResponseDTO dto = new OccurrenceResponseDTO(
                1L, 1L, "Rua Teste", "desc", "ABERTA", "FURTO", null, LocalDateTime.now(), null
        );

        when(occurrenceRepository.findAll()).thenReturn(List.of(occurrence));
        when(occurrenceDTOMapperService.toDTO(occurrence)).thenReturn(dto);

        List<OccurrenceResponseDTO> result = service.execute();

        assertEquals(1, result.size());
        assertSame(dto, result.get(0));
    }
}
