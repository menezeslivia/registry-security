package com.seguranca_urbana.backend.models.dtos.occurrence;

public record OccurrenceRequestDTO(
        String category,
        String description,
        String address,
        byte[] photo
) {}