package com.seguranca_urbana.backend.controllers.commoner;

import com.seguranca_urbana.backend.models.dtos.occurrence.OccurrenceRequestDTO;
import com.seguranca_urbana.backend.models.dtos.occurrence.OccurrenceResponseDTO;
import com.seguranca_urbana.backend.services.auth.JwtService;
import com.seguranca_urbana.backend.services.user.commoner.CreateOccurrenceService;
import com.seguranca_urbana.backend.services.user.commoner.GetOwnOccurrenceByIdService;
import com.seguranca_urbana.backend.services.user.commoner.GetOwnOccurrencesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cidadao/occurrences")
@AllArgsConstructor
@Tag(name = "Ocorrências do Cidadão", description = "Operações para cidadãos registrarem, consultarem e detalharem suas próprias ocorrências urbanas")
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

    @Operation(
        summary = "Registrar nova ocorrência",
        description = "Cadastra uma nova ocorrência urbana para o usuário autenticado.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = OccurrenceRequestDTO.class),
                examples = @ExampleObject(
                    name = "Exemplo de requisição",
                    value = """
                    {
                      "categoryId": 1,
                      "description": "Furto de bicicleta na praça central",
                      "address": "Praça Central, 123",
                      "photo": null
                    }
                    """
                )
            )
        ),
        responses = {
            @ApiResponse(
                responseCode = "201",
                description = "Ocorrência criada com sucesso",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = OccurrenceResponseDTO.class),
                    examples = @ExampleObject(
                        name = "Exemplo de resposta",
                        value = """
                        {
                          "id": 10,
                          "userId": 2,
                          "address": "Praça Central, 123",
                          "description": "Furto de bicicleta na praça central",
                          "status": "ABERTA",
                          "category": "FURTO",
                          "photoUrl": null,
                          "createdAt": "2025-05-28T12:00:00",
                          "updatedAt": null
                        }
                        """
                    )
                )
            ),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
        }
    )
    @PostMapping
    public ResponseEntity<OccurrenceResponseDTO> createOccurrence(
            @Parameter(hidden = true) HttpServletRequest request,
            @RequestBody OccurrenceRequestDTO dto
    ) {
        Long userId = extractUserIdFromRequest(request);
        OccurrenceResponseDTO response = createOccurrenceService.execute(userId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
        summary = "Listar ocorrências do próprio usuário",
        description = "Retorna todas as ocorrências cadastradas pelo usuário autenticado.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Lista de ocorrências do usuário",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = OccurrenceResponseDTO.class),
                    examples = @ExampleObject(
                        name = "Exemplo de resposta",
                        value = """
                        [
                          {
                            "id": 10,
                            "userId": 2,
                            "address": "Praça Central, 123",
                            "description": "Furto de bicicleta na praça central",
                            "status": "ABERTA",
                            "category": "FURTO",
                            "photoUrl": null,
                            "createdAt": "2025-05-28T12:00:00",
                            "updatedAt": null
                          }
                        ]
                        """
                    )
                )
            ),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
        }
    )
    @GetMapping
    public ResponseEntity<List<OccurrenceResponseDTO>> getOwnOccurrences(
            @Parameter(hidden = true) HttpServletRequest request
    ) {
        Long userId = extractUserIdFromRequest(request);
        List<OccurrenceResponseDTO> occurrences = getOwnOccurrencesService.execute(userId);
        return ResponseEntity.ok(occurrences);
    }

    @Operation(
        summary = "Detalhar ocorrência do próprio usuário",
        description = "Retorna os detalhes de uma ocorrência específica do usuário autenticado.",
        parameters = {
            @Parameter(name = "occurrenceId", description = "ID da ocorrência", required = true, example = "10")
        },
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Detalhes da ocorrência",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = OccurrenceResponseDTO.class),
                    examples = @ExampleObject(
                        name = "Exemplo de resposta",
                        value = """
                        {
                          "id": 10,
                          "userId": 2,
                          "address": "Praça Central, 123",
                          "description": "Furto de bicicleta na praça central",
                          "status": "ABERTA",
                          "category": "FURTO",
                          "photoUrl": null,
                          "createdAt": "2025-05-28T12:00:00",
                          "updatedAt": null
                        }
                        """
                    )
                )
            ),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Ocorrência não encontrada")
        }
    )
    @GetMapping("/{occurrenceId}")
    public ResponseEntity<OccurrenceResponseDTO> getOwnOccurrenceById(
            @Parameter(hidden = true) HttpServletRequest request,
            @PathVariable Long occurrenceId
    ) {
        Long userId = extractUserIdFromRequest(request);
        OccurrenceResponseDTO occurrence = getOwnOccurrenceByIdService.execute(userId, occurrenceId);
        return ResponseEntity.ok(occurrence);
    }
}
