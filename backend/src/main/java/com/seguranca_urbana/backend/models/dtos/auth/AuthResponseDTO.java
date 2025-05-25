package com.seguranca_urbana.backend.models.dtos.auth;

public record AuthResponseDTO(
        String token,
        String username,
        String userRole
) {}