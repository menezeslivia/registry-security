package com.seguranca_urbana.backend.adminServiceTests;

import com.seguranca_urbana.backend.models.dtos.user.UserRequestDTO;
import com.seguranca_urbana.backend.models.dtos.user.UserResponseDTO;
import com.seguranca_urbana.backend.models.user.User;
import com.seguranca_urbana.backend.repositorys.UserRepository;
import com.seguranca_urbana.backend.services.mappers.UserDTOMapperService;
import com.seguranca_urbana.backend.services.user.admin.AdminCreateUserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminCreateUserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserDTOMapperService userDTOMapperService;

    @InjectMocks
    private AdminCreateUserService service;

    @Test
    void testExecute_Success() {
        UserRequestDTO dto = mock(UserRequestDTO.class);
        User user = new User();
        UserResponseDTO responseDTO = mock(UserResponseDTO.class);

        when(userDTOMapperService.toEntity(dto)).thenReturn(user);
        when(userDTOMapperService.toDTO(user)).thenReturn(responseDTO);

        UserResponseDTO result = service.execute(dto);

        assertSame(responseDTO, result);
        verify(userRepository).save(user);
    }
}
