package com.seguranca_urbana.backend.controllers.admin;

import com.seguranca_urbana.backend.models.dtos.occurrence.OccurrenceRequestDTO;
import com.seguranca_urbana.backend.models.dtos.occurrence.OccurrenceResponseDTO;
import com.seguranca_urbana.backend.models.dtos.occurrence.OccurrenceUpdateDTO;
import com.seguranca_urbana.backend.services.auth.JwtService;
import com.seguranca_urbana.backend.services.user.admin.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/occurrences")
@AllArgsConstructor
public class AdminOccurrenceController {

    private final AdminCreateOccurrenceService createOccurrenceService;
    private final AdminGetAllOccurrencesService adminGetAllOccurrencesService;
    private final AdminGetOccurrenceByIdService adminGetOccurrenceByIdService;
    private final AdminGetOccurrencesByAddressService adminGetOccurrencesByAddressService;
    private final AdminUpdateOccurrenceService adminUpdateOccurrenceService;
    private final AdminUpdateOccurrenceStatusService adminUpdateOccurrenceStatusService;
    private final AdminDeleteOccurrenceService adminDeleteOccurrenceService;
    private final JwtService jwtService;

    // Utilitário para extrair userId do token JWT presente no header Authorization
    private Long extractAdminIdFromRequest(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Authorization header inválido ou ausente.");
        }
        String token = authorizationHeader.substring(7);
        return jwtService.extractUserId(token);
    }

    // Criação: admin pode associar a ocorrência a qualquer usuário usando o ID
    @PostMapping("/{userId}")
    public ResponseEntity<OccurrenceResponseDTO> create(
            HttpServletRequest request,
            @PathVariable Long userId,
            @RequestBody OccurrenceRequestDTO dto
    ) {
        Long adminId = extractAdminIdFromRequest(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createOccurrenceService.execute(userId, dto));
    }

    @GetMapping
    public ResponseEntity<List<OccurrenceResponseDTO>> getAll(HttpServletRequest request) {
        Long adminId = extractAdminIdFromRequest(request);
        return ResponseEntity.ok(adminGetAllOccurrencesService.execute());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OccurrenceResponseDTO> getById(HttpServletRequest request, @PathVariable Long id) {
        Long adminId = extractAdminIdFromRequest(request);
        return ResponseEntity.ok(adminGetOccurrenceByIdService.execute(id));
    }

    @GetMapping("/address/{address}")
    public ResponseEntity<List<OccurrenceResponseDTO>> getByAddress(HttpServletRequest request, @PathVariable String address) {
        Long adminId = extractAdminIdFromRequest(request);
        return ResponseEntity.ok(adminGetOccurrencesByAddressService.execute(address));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OccurrenceResponseDTO> update(
            HttpServletRequest request,
            @PathVariable Long id,
            @RequestBody OccurrenceUpdateDTO dto
    ) {
        Long adminId = extractAdminIdFromRequest(request);
        return ResponseEntity.ok(adminUpdateOccurrenceService.execute(id, dto));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<OccurrenceResponseDTO> updateStatus(
            HttpServletRequest request,
            @PathVariable Long id,
            @RequestBody OccurrenceUpdateDTO dto
    ) {
        Long adminId = extractAdminIdFromRequest(request);
        return ResponseEntity.ok(adminUpdateOccurrenceStatusService.execute(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(HttpServletRequest request, @PathVariable Long id) {
        Long adminId = extractAdminIdFromRequest(request);
        adminDeleteOccurrenceService.execute(id);
        return ResponseEntity.noContent().build();
    }
}