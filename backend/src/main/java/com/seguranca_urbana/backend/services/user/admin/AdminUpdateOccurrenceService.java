package com.seguranca_urbana.backend.services.user.admin;

import com.seguranca_urbana.backend.models.dtos.occurrence.OccurrenceResponseDTO;
import com.seguranca_urbana.backend.models.dtos.occurrence.OccurrenceUpdateDTO;
import com.seguranca_urbana.backend.models.occurrence.Occurrence;
import com.seguranca_urbana.backend.models.occurrence.OccurrenceCategory;
import com.seguranca_urbana.backend.repositorys.OccurrenceCategoryRepository;
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
public class AdminUpdateOccurrenceService {

    @Autowired
    private OccurrenceRepository occurrenceRepository;
    @Autowired
    private OccurrenceDTOMapperService occurrenceDTOMapperService;
    @Autowired
    private OccurrenceCategoryRepository categoryRepository;

    public OccurrenceResponseDTO execute(Long id, OccurrenceUpdateDTO dto) {
        Occurrence occurrence = occurrenceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Occurrence not found"));

        OccurrenceCategory category = null;
        if (dto.categoryId() != null) {
            category = categoryRepository.findById(dto.categoryId())
                    .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));
        }

        occurrence.update(category, dto);
        occurrenceRepository.save(occurrence);
        return occurrenceDTOMapperService.toDTO(occurrence);
    }
}