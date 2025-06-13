package com.seguranca_urbana.backend.models.dtos.user;

public record UserResponseDTO(
        Long id,
        String username,
        String userRole
) {}
