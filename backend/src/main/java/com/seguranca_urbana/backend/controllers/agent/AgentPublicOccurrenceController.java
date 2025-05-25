package com.seguranca_urbana.backend.controllers.agent;

import com.seguranca_urbana.backend.models.dtos.occurrence.OccurrenceResponseDTO;
import com.seguranca_urbana.backend.models.dtos.occurrence.OccurrenceUpdateDTO;
import com.seguranca_urbana.backend.models.enums.OccurrenceCategory;
import com.seguranca_urbana.backend.services.auth.JwtService;
import com.seguranca_urbana.backend.services.user.agent.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agent/occurrences")
@AllArgsConstructor
public class AgentPublicOccurrenceController {

    private final GetAllOccurrencesService getAllOccurrencesService;
    private final GetOccurrenceByIdService getOccurrenceByIdService;
    private final GetOccurrencesByAddressService getOccurrencesByAddressService;
    private final GetOccurrencesByCategoryService getOccurrencesByCategoryService;
    private final UpdateOccurrenceStatusService updateOccurrenceStatusService;
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

    @GetMapping
    public ResponseEntity<List<OccurrenceResponseDTO>> getAll(HttpServletRequest request) {
        Long agentId = extractUserIdFromRequest(request);
        // Se quiser usar o agentId para filtrar, passe para o service
        return ResponseEntity.ok(getAllOccurrencesService.execute());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OccurrenceResponseDTO> getById(HttpServletRequest request, @PathVariable Long id) {
        Long agentId = extractUserIdFromRequest(request);
        // Se quiser usar o agentId para filtrar, passe para o service
        return ResponseEntity.ok(getOccurrenceByIdService.execute(id));
    }

    @GetMapping("/address/{address}")
    public ResponseEntity<List<OccurrenceResponseDTO>> getByAddress(HttpServletRequest request, @PathVariable String address) {
        Long agentId = extractUserIdFromRequest(request);
        // Se quiser usar o agentId para filtrar, passe para o service
        return ResponseEntity.ok(getOccurrencesByAddressService.execute(address));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<OccurrenceResponseDTO>> getByCategory(HttpServletRequest request, @PathVariable OccurrenceCategory category) {
        Long agentId = extractUserIdFromRequest(request);
        // Se quiser usar o agentId para filtrar, passe para o service
        return ResponseEntity.ok(getOccurrencesByCategoryService.execute(category));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<OccurrenceResponseDTO> updateStatus(
            HttpServletRequest request,
            @PathVariable Long id,
            @RequestBody OccurrenceUpdateDTO dto
    ) {
        Long agentId = extractUserIdFromRequest(request);
        // Se quiser usar o agentId para auditar alterações, passe para o service
        return ResponseEntity.ok(updateOccurrenceStatusService.execute(id, dto));
    }
}