package com.seguranca_urbana.backend.services.occurrence;

import com.seguranca_urbana.backend.repositorys.OccurrenceCategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteCategoryService {

    @Autowired
    private OccurrenceCategoryRepository categoryRepository;

    public void execute(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new EntityNotFoundException("Categoria não encontrada");
        }
        categoryRepository.deleteById(id);
    }
}