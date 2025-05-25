package com.seguranca_urbana.backend.services.user.admin;

import com.seguranca_urbana.backend.models.dtos.user.UserResponseDTO;
import com.seguranca_urbana.backend.repositorys.UserRepository;
import com.seguranca_urbana.backend.services.mappers.UserDTOMapperService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class AdminGetAllUsersService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDTOMapperService userDTOMapperService;

    public List<UserResponseDTO> execute() {
        return userRepository.findAll()
                .stream()
                .map(userDTOMapperService::toDTO)
                .toList();
    }
}
