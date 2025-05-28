package com.seguranca_urbana.backend.models.dtos.occurrence;

public record OccurrenceRequestDTO(
        Long categoryId,
        String description,
        String address,
        byte[] photo
) {}