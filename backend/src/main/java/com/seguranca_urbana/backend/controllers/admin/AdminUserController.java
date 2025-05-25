package com.seguranca_urbana.backend.controllers.admin;

import com.seguranca_urbana.backend.models.dtos.user.UserRequestDTO;
import com.seguranca_urbana.backend.models.dtos.user.UserResponseDTO;
import com.seguranca_urbana.backend.models.dtos.user.UserUpdateDTO;
import com.seguranca_urbana.backend.services.auth.JwtService;
import com.seguranca_urbana.backend.services.user.admin.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/users")
@AllArgsConstructor
public class AdminUserController {

    private final AdminCreateUserService createUserService;
    private final AdminGetAllUsersService adminGetAllUsersService;
    private final AdminGetUserByIdService adminGetUserByIdService;
    private final AdminGetUserByUsernameService adminGetUserByUsernameService;
    private final AdminUpdateUserService adminUpdateUserService;
    private final AdminDeleteUserService adminDeleteUserService;
    private final JwtService jwtService;

    // Utilitário para extrair userId do token JWT presente no header Authorization
    private Long extractUserIdFromRequest(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Authorization header inválido ou ausente.");
        }
        String token = authorizationHeader.substring(7);
        return jwtService.extractUserId(token);
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> create(
            HttpServletRequest request,
            @RequestBody UserRequestDTO dto
    ) {
        Long adminId = extractUserIdFromRequest(request);
        // Se quiser auditar ou filtrar ações por adminId, basta passar para o service
        return ResponseEntity.status(HttpStatus.CREATED).body(createUserService.execute(dto));
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAll(HttpServletRequest request) {
        Long adminId = extractUserIdFromRequest(request);
        // Se quiser auditar ou filtrar ações por adminId, basta passar para o service
        return ResponseEntity.ok(adminGetAllUsersService.execute());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getById(HttpServletRequest request, @PathVariable Long id) {
        Long adminId = extractUserIdFromRequest(request);
        return ResponseEntity.ok(adminGetUserByIdService.execute(id));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserResponseDTO> getByUsername(HttpServletRequest request, @PathVariable String username) {
        Long adminId = extractUserIdFromRequest(request);
        return ResponseEntity.ok(adminGetUserByUsernameService.execute(username));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> update(
            HttpServletRequest request,
            @PathVariable Long id,
            @RequestBody UserUpdateDTO dto
    ) {
        Long adminId = extractUserIdFromRequest(request);
        return ResponseEntity.ok(adminUpdateUserService.execute(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(HttpServletRequest request, @PathVariable Long id) {
        Long adminId = extractUserIdFromRequest(request);
        adminDeleteUserService.execute(id);
        return ResponseEntity.noContent().build();
    }
}