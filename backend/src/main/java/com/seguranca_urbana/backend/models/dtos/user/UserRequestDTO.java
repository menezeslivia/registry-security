package com.seguranca_urbana.backend.models.dtos.user;

public record UserRequestDTO(
        String username,
        String password,
        String userRole
) {}