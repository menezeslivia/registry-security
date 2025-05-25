package com.seguranca_urbana.backend.services.user.agent;

import com.seguranca_urbana.backend.models.dtos.occurrence.OccurrenceResponseDTO;
import com.seguranca_urbana.backend.models.dtos.occurrence.OccurrenceUpdateDTO;
import com.seguranca_urbana.backend.models.occurrence.Occurrence;
import com.seguranca_urbana.backend.repositorys.OccurrenceRepository;
import com.seguranca_urbana.backend.services.mappers.OccurrenceDTOMapperService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class UpdateOccurrenceStatusService {

    @Autowired
    private OccurrenceRepository occurrenceRepository;
    @Autowired
    private OccurrenceDTOMapperService occurrenceDTOMapperService;

    public OccurrenceResponseDTO execute(Long id, OccurrenceUpdateDTO dto) {
        Occurrence occurrence = occurrenceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Occurrence not found"));
        // Só atualiza o status, mantendo outros campos inalterados
        if (dto.status() != null) {
            occurrence.setStatus(dto.status());
        }
        occurrenceRepository.save(occurrence);
        return occurrenceDTOMapperService.toDTO(occurrence);
    }
}
