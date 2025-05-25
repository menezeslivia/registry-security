package com.seguranca_urbana.backend.repositorys;

import com.seguranca_urbana.backend.models.occurrence.Occurrence;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OccurrenceRepository extends JpaRepository<Occurrence, Long> {
    // Buscar por categoria, se desejar
    List<Occurrence> findByCategory(String category);

    // Buscar por usuário
    List<Occurrence> findByUserId(Long userId);

    //Buscar por endereço
    List<Occurrence> findByAddress(String address);

    // Regra de negócio: não pode haver duas ocorrências idênticas (mesmo endereço + mesma descrição) em menos de 24h
    boolean existsByAddressAndDescriptionAndCreatedAtAfter(
            String address, String description, LocalDateTime after
    );
}