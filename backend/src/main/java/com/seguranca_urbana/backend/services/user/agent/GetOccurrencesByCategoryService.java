package com.seguranca_urbana.backend.services.user.agent;

import com.seguranca_urbana.backend.models.dtos.occurrence.OccurrenceResponseDTO;
import com.seguranca_urbana.backend.models.occurrence.OccurrenceCategory;
import com.seguranca_urbana.backend.repositorys.OccurrenceCategoryRepository;
import com.seguranca_urbana.backend.repositorys.OccurrenceRepository;
import com.seguranca_urbana.backend.services.mappers.OccurrenceDTOMapperService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class GetOccurrencesByCategoryService {

    @Autowired
    private OccurrenceRepository occurrenceRepository;
    @Autowired
    private OccurrenceCategoryRepository occurrenceCategoryRepository;
    @Autowired
    private OccurrenceDTOMapperService occurrenceDTOMapperService;

    public List<OccurrenceResponseDTO> execute(String categoryName) {
        OccurrenceCategory category = occurrenceCategoryRepository.findByName(categoryName)
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));
        return occurrenceRepository.findByCategory(category).stream()
                .map(occurrenceDTOMapperService::toDTO)
                .toList();
    }
}