package com.seguranca_urbana.backend.models.dtos.occurrence;

import java.time.LocalDateTime;

public record OccurrenceResponseDTO(
        Long id,
        Long categoryId,    // novo campo
        String categoryName,// novo campo
        String status,
        String description,
        String address,
        byte[] photo,
        LocalDateTime createdAt,
        Long userId
) {}