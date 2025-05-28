package com.seguranca_urbana.backend.services.mappers;

import com.seguranca_urbana.backend.models.dtos.occurrence.OccurrenceRequestDTO;
import com.seguranca_urbana.backend.models.dtos.occurrence.OccurrenceResponseDTO;
import com.seguranca_urbana.backend.models.enums.OccurrenceStatus;
import com.seguranca_urbana.backend.models.occurrence.Occurrence;
import com.seguranca_urbana.backend.models.occurrence.OccurrenceCategory;
import com.seguranca_urbana.backend.models.user.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OccurrenceDTOMapperService {

    public Occurrence toEntity(OccurrenceRequestDTO dto, User user, OccurrenceCategory category) {
        return Occurrence.builder()
                .user(user)
                .category(category)
                .status(OccurrenceStatus.PENDING)
                .description(dto.description())
                .address(dto.address())
                .photo(dto.photo())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public OccurrenceResponseDTO toDTO(Occurrence occurrence) {
        return new OccurrenceResponseDTO(
                occurrence.getId(),
                occurrence.getCategory().getId(),           // retorna o id da categoria
                occurrence.getCategory().getName(),         // retorna o nome da categoria
                occurrence.getStatus().name(),
                occurrence.getDescription(),
                occurrence.getAddress(),
                occurrence.getPhoto(),
                occurrence.getCreatedAt(),
                occurrence.getUser() != null ? occurrence.getUser().getId() : null
        );
    }
}
