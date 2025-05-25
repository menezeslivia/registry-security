package com.seguranca_urbana.backend.services.user.commoner;

import com.seguranca_urbana.backend.models.dtos.occurrence.OccurrenceResponseDTO;
import com.seguranca_urbana.backend.models.occurrence.Occurrence;
import com.seguranca_urbana.backend.repositorys.OccurrenceRepository;
import com.seguranca_urbana.backend.services.mappers.OccurrenceDTOMapperService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetOwnOccurrenceByIdService {

    @Autowired
    private OccurrenceRepository occurrenceRepository;
    @Autowired
    private OccurrenceDTOMapperService occurrenceDTOMapperService;

    public OccurrenceResponseDTO execute(Long userId, Long occurrenceId) {
        Occurrence occurrence = occurrenceRepository.findByIdAndUserId(occurrenceId, userId);
        if (occurrence == null) {
            throw new EntityNotFoundException("Ocorrência não encontrada para este usuário");
        }
        return occurrenceDTOMapperService.toDTO(occurrence);
    }
}