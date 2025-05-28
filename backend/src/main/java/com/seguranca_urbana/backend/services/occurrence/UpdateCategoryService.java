package com.seguranca_urbana.backend.services.occurrence;

import com.seguranca_urbana.backend.models.dtos.occurrence.OccurrenceCategoryResponseDTO;
import com.seguranca_urbana.backend.models.dtos.occurrence.OccurrenceCategoryUpdateDTO;
import com.seguranca_urbana.backend.models.occurrence.OccurrenceCategory;
import com.seguranca_urbana.backend.repositorys.OccurrenceCategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateCategoryService {

    @Autowired
    private OccurrenceCategoryRepository categoryRepository;

    public OccurrenceCategoryResponseDTO execute(Long id, OccurrenceCategoryUpdateDTO dto) {
        OccurrenceCategory category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));
        if (dto.name() != null && !dto.name().isBlank()) {
            category.setName(dto.name());
        }
        categoryRepository.save(category);
        return new OccurrenceCategoryResponseDTO(category.getId(), category.getName());
    }
}
