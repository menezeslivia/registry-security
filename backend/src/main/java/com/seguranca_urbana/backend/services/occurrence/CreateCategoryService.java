package com.seguranca_urbana.backend.services.occurrence;


import com.seguranca_urbana.backend.models.dtos.occurrence.OccurrenceCategoryRequestDTO;
import com.seguranca_urbana.backend.models.dtos.occurrence.OccurrenceCategoryResponseDTO;
import com.seguranca_urbana.backend.models.dtos.occurrence.OccurrenceRequestDTO;
import com.seguranca_urbana.backend.models.occurrence.OccurrenceCategory;
import com.seguranca_urbana.backend.repositorys.OccurrenceCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateCategoryService {

    @Autowired
    private OccurrenceCategoryRepository categoryRepository;

    public OccurrenceCategoryResponseDTO execute(OccurrenceCategoryRequestDTO dto) {
        if (categoryRepository.existsByName(dto.name())) {
            throw new IllegalArgumentException("Categoria já existe.");
        }
        OccurrenceCategory category = new OccurrenceCategory(dto.name());
        categoryRepository.save(category);
        return new OccurrenceCategoryResponseDTO(category.getId(), category.getName());
    }
}