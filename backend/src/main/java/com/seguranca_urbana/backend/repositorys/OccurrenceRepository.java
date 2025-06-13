package com.seguranca_urbana.backend.repositorys;

import com.seguranca_urbana.backend.models.occurrence.Occurrence;
import com.seguranca_urbana.backend.models.occurrence.OccurrenceCategory;
import com.seguranca_urbana.backend.models.enums.OccurrenceStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OccurrenceRepository extends JpaRepository<Occurrence, Long> {
    List<Occurrence> findAllByUserId(Long userId);
    Occurrence findByIdAndUserId(Long occurrenceId, Long userId);
    boolean existsByAddressAndDescriptionAndCreatedAtAfter(String address, String description, LocalDateTime after);

    List<Occurrence> findByAddress(String address);
    List<Occurrence> findByCategory(OccurrenceCategory category);
    List<Occurrence> findByCategoryName(String name);

    List<Occurrence> findByStatus(OccurrenceStatus status);
    List<Occurrence> findByCategoryAndStatus(OccurrenceCategory category, OccurrenceStatus status);
}