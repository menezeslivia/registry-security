package com.seguranca_urbana.backend.adminServiceTests;

import com.seguranca_urbana.backend.models.dtos.occurrence.OccurrenceResponseDTO;
import com.seguranca_urbana.backend.models.occurrence.Occurrence;
import com.seguranca_urbana.backend.repositorys.OccurrenceRepository;
import com.seguranca_urbana.backend.services.mappers.OccurrenceDTOMapperService;
import com.seguranca_urbana.backend.services.user.admin.AdminGetOccurrencesByAddressService;
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
class AdminGetOccurrencesByAddressServiceTest {

    @Mock
    private OccurrenceRepository occurrenceRepository;
    @Mock
    private OccurrenceDTOMapperService occurrenceDTOMapperService;

    @InjectMocks
    private AdminGetOccurrencesByAddressService service;

    @Test
    void testExecute_ReturnsDTOList() {
        String address = "Rua Y";
        Occurrence occurrence = new Occurrence();
        OccurrenceResponseDTO dto = new OccurrenceResponseDTO(
                1L, 2L, address, "desc", "ABERTA", "ROUBO", null, LocalDateTime.now(), null
        );

        when(occurrenceRepository.findByAddress(address)).thenReturn(List.of(occurrence));
        when(occurrenceDTOMapperService.toDTO(occurrence)).thenReturn(dto);

        List<OccurrenceResponseDTO> result = service.execute(address);

        assertEquals(1, result.size());
        assertSame(dto, result.get(0));
    }
}
