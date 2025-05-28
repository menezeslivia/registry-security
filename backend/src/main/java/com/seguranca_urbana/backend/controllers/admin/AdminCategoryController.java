package com.seguranca_urbana.backend.controllers.admin;

import com.seguranca_urbana.backend.models.dtos.occurrence.OccurrenceCategoryRequestDTO;
import com.seguranca_urbana.backend.models.dtos.occurrence.OccurrenceCategoryResponseDTO;
import com.seguranca_urbana.backend.models.dtos.occurrence.OccurrenceCategoryUpdateDTO;
import com.seguranca_urbana.backend.services.occurrence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/categories")
public class AdminCategoryController {

    @Autowired
    private CreateCategoryService createCategoryService;
    @Autowired
    private UpdateCategoryService updateCategoryService;
    @Autowired
    private DeleteCategoryService deleteCategoryService;
    @Autowired
    private GetAllCategoriesService getAllCategoriesService;
    @Autowired
    private GetCategoryByIdService getCategoryByIdService;

    @PostMapping
    public ResponseEntity<OccurrenceCategoryResponseDTO> create(@RequestBody OccurrenceCategoryRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(createCategoryService.execute(dto));
    }

    @GetMapping
    public ResponseEntity<List<OccurrenceCategoryResponseDTO>> getAll() {
        return ResponseEntity.ok(getAllCategoriesService.execute());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OccurrenceCategoryResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(getCategoryByIdService.execute(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OccurrenceCategoryResponseDTO> update(@PathVariable Long id, @RequestBody OccurrenceCategoryUpdateDTO dto) {
        return ResponseEntity.ok(updateCategoryService.execute(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deleteCategoryService.execute(id);
        return ResponseEntity.noContent().build();
    }
}
