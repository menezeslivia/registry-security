package com.seguranca_urbana.backend.adminServiceTests;

import com.seguranca_urbana.backend.models.dtos.occurrence.OccurrenceRequestDTO;
import com.seguranca_urbana.backend.models.dtos.occurrence.OccurrenceResponseDTO;
import com.seguranca_urbana.backend.models.occurrence.Occurrence;
import com.seguranca_urbana.backend.models.occurrence.OccurrenceCategory;
import com.seguranca_urbana.backend.models.user.User;
import com.seguranca_urbana.backend.repositorys.OccurrenceCategoryRepository;
import com.seguranca_urbana.backend.repositorys.OccurrenceRepository;
import com.seguranca_urbana.backend.repositorys.UserRepository;
import com.seguranca_urbana.backend.services.mappers.OccurrenceDTOMapperService;
import com.seguranca_urbana.backend.services.user.admin.AdminCreateOccurrenceService;
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
class AdminCreateOccurrenceServiceTest {

    @Mock
    private OccurrenceRepository occurrenceRepository;
    @Mock
    private OccurrenceDTOMapperService occurrenceDTOMapperService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private OccurrenceCategoryRepository categoryRepository;

    @InjectMocks
    private AdminCreateOccurrenceService service;

    @Test
    void testExecute_Success() {
        Long userId = 1L;
        Long categoryId = 10L;
        OccurrenceRequestDTO dto = mock(OccurrenceRequestDTO.class);
        when(dto.categoryId()).thenReturn(categoryId);

        User user = new User();
        OccurrenceCategory category = new OccurrenceCategory();
        Occurrence occurrence = new Occurrence();
        OccurrenceResponseDTO responseDTO = new OccurrenceResponseDTO(
                1L, userId, "Rua X", "desc", "ABERTA", "FURTO", null, LocalDateTime.now(), null
        );

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(occurrenceDTOMapperService.toEntity(dto, user, category)).thenReturn(occurrence);
        when(occurrenceDTOMapperService.toDTO(occurrence)).thenReturn(responseDTO);

        OccurrenceResponseDTO result = service.execute(userId, dto);

        assertSame(responseDTO, result);
        verify(occurrenceRepository).save(occurrence);
    }

    @Test
    void testExecute_UserNotFound_ThrowsException() {
        Long userId = 1L;
        OccurrenceRequestDTO dto = mock(OccurrenceRequestDTO.class);
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.execute(userId, dto));
    }

    @Test
    void testExecute_CategoryNotFound_ThrowsException() {
        Long userId = 1L;
        Long categoryId = 2L;
        OccurrenceRequestDTO dto = mock(OccurrenceRequestDTO.class);
        when(dto.categoryId()).thenReturn(categoryId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(new User()));
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.execute(userId, dto));
    }
}
