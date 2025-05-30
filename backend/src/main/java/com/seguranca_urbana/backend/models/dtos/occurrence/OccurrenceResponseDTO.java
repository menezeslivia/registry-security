package com.seguranca_urbana.backend.models.dtos.occurrence;

import java.time.LocalDateTime;

public record OccurrenceResponseDTO(
        Long id,
        Long categoryId,
        String categoryName,
        String status,
        String description,
        String address,
        byte[] photo,
        LocalDateTime createdAt,
        Long userId
) {}