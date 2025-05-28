package com.seguranca_urbana.backend.models.dtos.occurrence;

import com.seguranca_urbana.backend.models.enums.OccurrenceCategory;
import com.seguranca_urbana.backend.models.enums.OccurrenceStatus;

public record OccurrenceUpdateDTO(
        Long categoryId,
        OccurrenceStatus status,
        String description,
        String address,
        byte[] photo
) {
}

