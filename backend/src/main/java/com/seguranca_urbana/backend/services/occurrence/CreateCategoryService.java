package com.seguranca_urbana.backend.services.occurrence;

import com.seguranca_urbana.backend.models.dtos.occurrence.OccurrenceCategoryRequestDTO;
import com.seguranca_urbana.backend.models.dtos.occurrence.OccurrenceCategoryResponseDTO;
import com.seguranca_urbana.backend.models.occurrence.OccurrenceCategory;
import com.seguranca_urbana.backend.repositorys.OccurrenceCategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CreateCategoryService {

    @Autowired
    private OccurrenceCategoryRepository categoryRepository;

    public OccurrenceCategoryResponseDTO execute(OccurrenceCategoryRequestDTO dto) {
        log.info("Recebida solicitação para criar categoria: {}", dto.getName());

        if (categoryRepository.existsByName(dto.getName())) {
            log.warn("Tentativa de criar categoria duplicada: {}", dto.getName());
            throw new IllegalArgumentException("Categoria já existe.");
        }

        OccurrenceCategory category = new OccurrenceCategory(dto.getName());
        categoryRepository.save(category);

        log.info("Categoria criada com sucesso: ID={}, Nome={}", category.getId(), category.getName());

        return new OccurrenceCategoryResponseDTO(category.getId(), category.getName());
    }
}