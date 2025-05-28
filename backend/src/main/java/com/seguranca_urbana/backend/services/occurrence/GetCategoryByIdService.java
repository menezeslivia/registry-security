package com.seguranca_urbana.backend.services.occurrence;

import com.seguranca_urbana.backend.models.dtos.occurrence.OccurrenceCategoryResponseDTO;
import com.seguranca_urbana.backend.models.occurrence.OccurrenceCategory;
import com.seguranca_urbana.backend.repositorys.OccurrenceCategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetCategoryByIdService {

    @Autowired
    private OccurrenceCategoryRepository categoryRepository;

    public OccurrenceCategoryResponseDTO execute(Long id) {
        OccurrenceCategory category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));
        return new OccurrenceCategoryResponseDTO(category.getId(), category.getName());
    }
}
