package com.seguranca_urbana.backend.services.user.admin;

import com.seguranca_urbana.backend.models.dtos.user.UserResponseDTO;
import com.seguranca_urbana.backend.models.enums.UserRole;
import com.seguranca_urbana.backend.models.user.User;
import com.seguranca_urbana.backend.repositorys.UserRepository;
import com.seguranca_urbana.backend.services.mappers.UserDTOMapperService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class AdminUpdateUserRoleService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDTOMapperService userDTOMapperService;

    public UserResponseDTO execute(Long id, String newType) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        user.setUserRole(UserRole.valueOf(newType));
        userRepository.save(user);
        return userDTOMapperService.toDTO(user);
    }
}
