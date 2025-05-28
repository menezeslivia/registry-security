package com.seguranca_urbana.backend.services.occurrence;

import com.seguranca_urbana.backend.models.dtos.occurrence.OccurrenceCategoryResponseDTO;
import com.seguranca_urbana.backend.repositorys.OccurrenceCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllCategoriesService {

    @Autowired
    private OccurrenceCategoryRepository categoryRepository;

    public List<OccurrenceCategoryResponseDTO> execute() {
        return categoryRepository.findAll()
                .stream()
                .map(category -> new OccurrenceCategoryResponseDTO(category.getId(), category.getName()))
                .toList();
    }
}