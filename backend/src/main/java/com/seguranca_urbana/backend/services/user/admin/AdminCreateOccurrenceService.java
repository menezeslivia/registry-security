package com.seguranca_urbana.backend.services.user.admin;

import com.seguranca_urbana.backend.models.dtos.occurrence.OccurrenceRequestDTO;
import com.seguranca_urbana.backend.models.dtos.occurrence.OccurrenceResponseDTO;
import com.seguranca_urbana.backend.models.occurrence.Occurrence;
import com.seguranca_urbana.backend.models.user.User;
import com.seguranca_urbana.backend.repositorys.OccurrenceRepository;
import com.seguranca_urbana.backend.repositorys.UserRepository;
import com.seguranca_urbana.backend.services.mappers.OccurrenceDTOMapperService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class AdminCreateOccurrenceService {

    @Autowired
    private OccurrenceRepository occurrenceRepository;
    @Autowired
    private OccurrenceDTOMapperService occurrenceDTOMapperService;
    @Autowired
    private UserRepository userRepository;

    public OccurrenceResponseDTO execute(Long userId, OccurrenceRequestDTO dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Occurrence occurrence = occurrenceDTOMapperService.toEntity(dto, user);
        occurrenceRepository.save(occurrence);
        return occurrenceDTOMapperService.toDTO(occurrence);
    }
}