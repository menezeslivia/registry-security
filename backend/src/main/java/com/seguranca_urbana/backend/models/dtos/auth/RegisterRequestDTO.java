package com.seguranca_urbana.backend.models.dtos.auth;


public record RegisterRequestDTO(
        String username,
        String password,
        String userRole // "CIDADAO", "AGENTE_PUBLICO", "ADMIN"
) {}
