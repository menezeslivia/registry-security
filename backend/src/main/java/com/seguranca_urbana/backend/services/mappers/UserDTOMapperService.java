package com.seguranca_urbana.backend.services.mappers;

import com.seguranca_urbana.backend.models.dtos.user.UserRequestDTO;
import com.seguranca_urbana.backend.models.dtos.user.UserResponseDTO;
import com.seguranca_urbana.backend.models.enums.UserRole;
import com.seguranca_urbana.backend.models.user.User;
import org.springframework.stereotype.Service;

@Service
public class UserDTOMapperService {

    public User toEntity(UserRequestDTO dto) {
        return User.builder()
                .username(dto.username())
                .password(dto.password())
                .userRole(UserRole.valueOf(dto.userRole()))
                .build();
    }

    public UserResponseDTO toDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getUserRole().name()
        );
    }
}
