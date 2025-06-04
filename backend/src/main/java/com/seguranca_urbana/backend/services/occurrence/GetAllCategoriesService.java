package com.seguranca_urbana.backend.services.occurrence;

import com.seguranca_urbana.backend.models.dtos.occurrence.OccurrenceCategoryResponseDTO;
import com.seguranca_urbana.backend.repositorys.OccurrenceCategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class GetAllCategoriesService {

    @Autowired
    private OccurrenceCategoryRepository categoryRepository;

    public List<OccurrenceCategoryResponseDTO> execute() {
        log.info("Iniciando listagem de todas as categorias de ocorrência.");

        List<OccurrenceCategoryResponseDTO> categories = categoryRepository.findAll()
                .stream()
                .map(category -> new OccurrenceCategoryResponseDTO(category.getId(), category.getName()))
                .toList();

        log.info("Total de categorias encontradas: {}", categories.size());

        return categories;
    }
}
