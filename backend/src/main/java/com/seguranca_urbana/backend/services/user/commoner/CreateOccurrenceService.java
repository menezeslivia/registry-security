package com.seguranca_urbana.backend.services.user.commoner;

import com.seguranca_urbana.backend.models.dtos.occurrence.OccurrenceRequestDTO;
import com.seguranca_urbana.backend.models.dtos.occurrence.OccurrenceResponseDTO;
import com.seguranca_urbana.backend.models.occurrence.Occurrence;
import com.seguranca_urbana.backend.models.occurrence.OccurrenceCategory;
import com.seguranca_urbana.backend.models.user.User;
import com.seguranca_urbana.backend.repositorys.OccurrenceCategoryRepository;
import com.seguranca_urbana.backend.repositorys.OccurrenceRepository;
import com.seguranca_urbana.backend.repositorys.UserRepository;
import com.seguranca_urbana.backend.services.mappers.OccurrenceDTOMapperService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CreateOccurrenceService {

    @Autowired
    private OccurrenceRepository occurrenceRepository;
    @Autowired
    private OccurrenceDTOMapperService occurrenceDTOMapperService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OccurrenceCategoryRepository categoryRepository;

    public OccurrenceResponseDTO execute(Long userId, OccurrenceRequestDTO dto) {
        if (dto.address() == null || dto.address().isBlank()) {
            throw new IllegalArgumentException("Endereço obrigatório.");
        }

        // Verifica duplicidade no mesmo endereço e descrição nas últimas 24h
        LocalDateTime since = LocalDateTime.now().minusHours(24);
        boolean exists = occurrenceRepository.existsByAddressAndDescriptionAndCreatedAtAfter(
                dto.address(), dto.description(), since);
        if (exists) {
            throw new IllegalArgumentException("Já existe uma ocorrência idêntica neste endereço nas últimas 24h.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        OccurrenceCategory category = categoryRepository.findById(dto.categoryId())
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));

        Occurrence occurrence = occurrenceDTOMapperService.toEntity(dto, user, category);
        occurrenceRepository.save(occurrence);
        return occurrenceDTOMapperService.toDTO(occurrence);
    }
}