package com.seguranca_urbana.backend.services.user.admin;

import com.seguranca_urbana.backend.models.dtos.user.UserResponseDTO;
import com.seguranca_urbana.backend.models.dtos.user.UserUpdateDTO;
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
public class AdminUpdateUserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDTOMapperService userDTOMapperService;

    public UserResponseDTO execute(Long id, UserUpdateDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        user.update(dto);
        userRepository.save(user);
        return userDTOMapperService.toDTO(user);
    }
}
