package com.seguranca_urbana.backend.services.user.admin;

import com.seguranca_urbana.backend.models.dtos.user.UserRequestDTO;
import com.seguranca_urbana.backend.models.dtos.user.UserResponseDTO;
import com.seguranca_urbana.backend.models.user.User;
import com.seguranca_urbana.backend.repositorys.UserRepository;
import com.seguranca_urbana.backend.services.mappers.UserDTOMapperService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDTOMapperService userDTOMapperService;

    public UserResponseDTO execute(UserRequestDTO dto) {
        User user = userDTOMapperService.toEntity(dto);
        userRepository.save(user);
        return userDTOMapperService.toDTO(user);
    }
}
