package com.seguranca_urbana.backend.adminServiceTests;

import com.seguranca_urbana.backend.repositorys.UserRepository;
import com.seguranca_urbana.backend.services.user.admin.AdminDeleteUserService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminDeleteUserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AdminDeleteUserService service;

    @Test
    void testExecute_Success() {
        Long id = 3L;
        when(userRepository.existsById(id)).thenReturn(true);

        service.execute(id);

        verify(userRepository).deleteById(id);
    }

    @Test
    void testExecute_NotFound_ThrowsException() {
        Long id = 4L;
        when(userRepository.existsById(id)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> service.execute(id));
        verify(userRepository, never()).deleteById(any());
    }
}
