package com.seguranca_urbana.backend.controllers.commoner;

import com.seguranca_urbana.backend.models.dtos.occurrence.OccurrenceRequestDTO;
import com.seguranca_urbana.backend.models.dtos.occurrence.OccurrenceResponseDTO;
import com.seguranca_urbana.backend.services.auth.JwtService;
import com.seguranca_urbana.backend.services.user.commoner.CreateOccurrenceService;
import com.seguranca_urbana.backend.services.user.commoner.GetOwnOccurrenceByIdService;
import com.seguranca_urbana.backend.services.user.commoner.GetOwnOccurrencesService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cidadao/occurrences")
@AllArgsConstructor
public class CommonerOccurrenceController {

    private final CreateOccurrenceService createOccurrenceService;
    private final GetOwnOccurrencesService getOwnOccurrencesService;
    private final GetOwnOccurrenceByIdService getOwnOccurrenceByIdService;
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

    // Criação de ocorrência pelo cidadão
    @PostMapping
    public ResponseEntity<OccurrenceResponseDTO> createOccurrence(
            HttpServletRequest request,
            @RequestBody OccurrenceRequestDTO dto
    ) {
        Long userId = extractUserIdFromRequest(request);
        OccurrenceResponseDTO response = createOccurrenceService.execute(userId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Listar todas as ocorrências do próprio cidadão
    @GetMapping
    public ResponseEntity<List<OccurrenceResponseDTO>> getOwnOccurrences(HttpServletRequest request) {
        Long userId = extractUserIdFromRequest(request);
        List<OccurrenceResponseDTO> occurrences = getOwnOccurrencesService.execute(userId);
        return ResponseEntity.ok(occurrences);
    }

    // Buscar ocorrência específica do próprio cidadão
    @GetMapping("/{occurrenceId}")
    public ResponseEntity<OccurrenceResponseDTO> getOwnOccurrenceById(
            HttpServletRequest request,
            @PathVariable Long occurrenceId
    ) {
        Long userId = extractUserIdFromRequest(request);
        OccurrenceResponseDTO occurrence = getOwnOccurrenceByIdService.execute(userId, occurrenceId);
        return ResponseEntity.ok(occurrence);
    }
}