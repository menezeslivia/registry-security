package com.seguranca_urbana.backend.repositorys;

import com.seguranca_urbana.backend.models.occurrence.Occurrence;
import com.seguranca_urbana.backend.models.occurrence.OccurrenceCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OccurrenceRepository extends JpaRepository<Occurrence, Long> {
    // Para CIDADÃO: buscar ocorrências do próprio usuário
    List<Occurrence> findAllByUserId(Long userId);

    // Para CIDADÃO: buscar ocorrência específica do próprio usuário
    Occurrence findByIdAndUserId(Long occurrenceId, Long userId);

    // Para CIDADÃO: validação de duplicidade em 24h
    boolean existsByAddressAndDescriptionAndCreatedAtAfter(String address, String description, LocalDateTime after);

    // Para AGENTE/ADMIN: buscar por endereço
    List<Occurrence> findByAddress(String address);

    // Para AGENTE/ADMIN: buscar por categoria
    List<Occurrence> findByCategory(OccurrenceCategory category);

    // Buscar por nome da categoria
    List<Occurrence> findByCategoryName(String name);

    // (Opcional) Buscar por status
    List<Occurrence> findByStatus(String status);

    // (Opcional) Filtros combinados
    List<Occurrence> findByCategoryAndStatus(OccurrenceCategory category, String status);
}