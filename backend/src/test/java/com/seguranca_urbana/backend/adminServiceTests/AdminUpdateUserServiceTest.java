package com.seguranca_urbana.backend.adminServiceTests;

import com.seguranca_urbana.backend.models.dtos.user.UserResponseDTO;
import com.seguranca_urbana.backend.models.dtos.user.UserUpdateDTO;
import com.seguranca_urbana.backend.models.user.User;
import com.seguranca_urbana.backend.repositorys.UserRepository;
import com.seguranca_urbana.backend.services.mappers.UserDTOMapperService;
import com.seguranca_urbana.backend.services.user.admin.AdminUpdateUserService;
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
class AdminUpdateUserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserDTOMapperService userDTOMapperService;

    @InjectMocks
    private AdminUpdateUserService service;

    @Test
    void testExecute_UserFound_UpdatesAndReturnsDTO() {
        Long id = 1L;
        UserUpdateDTO dto = mock(UserUpdateDTO.class);
        User user = mock(User.class);
        UserResponseDTO responseDTO = mock(UserResponseDTO.class);

        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userDTOMapperService.toDTO(user)).thenReturn(responseDTO);

        UserResponseDTO result = service.execute(id, dto);

        assertSame(responseDTO, result);
        verify(user).update(dto);
        verify(userRepository).save(user);
    }

    @Test
    void testExecute_UserNotFound_ThrowsException() {
        Long id = 1L;
        UserUpdateDTO dto = mock(UserUpdateDTO.class);
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.execute(id, dto));
    }
}
