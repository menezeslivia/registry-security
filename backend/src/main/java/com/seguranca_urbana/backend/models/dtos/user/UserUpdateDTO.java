package com.seguranca_urbana.backend.models.dtos.user;

import com.seguranca_urbana.backend.models.enums.UserRole;

public record UserUpdateDTO(
        String username,
        String password,
        UserRole userRole
) {
}