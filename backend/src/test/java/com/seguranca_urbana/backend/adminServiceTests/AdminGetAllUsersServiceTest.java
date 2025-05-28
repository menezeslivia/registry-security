package com.seguranca_urbana.backend.adminServiceTests;

import com.seguranca_urbana.backend.models.dtos.user.UserResponseDTO;
import com.seguranca_urbana.backend.models.user.User;
import com.seguranca_urbana.backend.repositorys.UserRepository;
import com.seguranca_urbana.backend.services.mappers.UserDTOMapperService;
import com.seguranca_urbana.backend.services.user.admin.AdminGetAllUsersService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminGetAllUsersServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserDTOMapperService userDTOMapperService;

    @InjectMocks
    private AdminGetAllUsersService service;

    @Test
    void testExecute_ReturnsAllDTOs() {
        User user = new User();
        UserResponseDTO dto = mock(UserResponseDTO.class);

        when(userRepository.findAll()).thenReturn(List.of(user));
        when(userDTOMapperService.toDTO(user)).thenReturn(dto);

        List<UserResponseDTO> result = service.execute();

        assertEquals(1, result.size());
        assertSame(dto, result.get(0));
    }
}
