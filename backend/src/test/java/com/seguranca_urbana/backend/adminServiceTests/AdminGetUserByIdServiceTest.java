package com.seguranca_urbana.backend.adminServiceTests;

import com.seguranca_urbana.backend.models.dtos.user.UserResponseDTO;
import com.seguranca_urbana.backend.models.user.User;
import com.seguranca_urbana.backend.repositorys.UserRepository;
import com.seguranca_urbana.backend.services.mappers.UserDTOMapperService;
import com.seguranca_urbana.backend.services.user.admin.AdminGetUserByIdService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminGetUserByIdServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserDTOMapperService userDTOMapperService;

    @InjectMocks
    private AdminGetUserByIdService service;

    @Test
    void testExecute_UserFound_ReturnsDTO() {
        Long id = 1L;
        User user = new User();
        UserResponseDTO dto = mock(UserResponseDTO.class);

        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userDTOMapperService.toDTO(user)).thenReturn(dto);

        UserResponseDTO result = service.execute(id);

        assertSame(dto, result);
    }

    @Test
    void testExecute_UserNotFound_ThrowsException() {
        Long id = 1L;
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.execute(id));
    }
}