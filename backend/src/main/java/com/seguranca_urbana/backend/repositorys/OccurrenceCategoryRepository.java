package com.seguranca_urbana.backend.repositorys;

import com.seguranca_urbana.backend.models.occurrence.OccurrenceCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OccurrenceCategoryRepository extends JpaRepository<OccurrenceCategory, Long> {
    Optional<OccurrenceCategory> findByName(String name);
    boolean existsByName(String name);
}
