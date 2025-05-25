package com.seguranca_urbana.backend.models.dtos.auth;

public record LoginRequestDTO(
        String username,
        String password
) {}
