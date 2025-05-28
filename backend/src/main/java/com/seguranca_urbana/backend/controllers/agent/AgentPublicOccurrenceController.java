package com.seguranca_urbana.backend.controllers.agent;

import com.seguranca_urbana.backend.models.dtos.occurrence.OccurrenceResponseDTO;
import com.seguranca_urbana.backend.models.dtos.occurrence.OccurrenceUpdateDTO;
import com.seguranca_urbana.backend.services.auth.JwtService;
import com.seguranca_urbana.backend.services.user.agent.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agent/occurrences")
@AllArgsConstructor
@Tag(name = "Ocorrências para Agentes", description = "Operações para agentes consultarem e atualizarem ocorrências urbanas")
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

    @Operation(
        summary = "Listar todas as ocorrências",
        description = "Retorna todas as ocorrências registradas no sistema. Disponível apenas para agentes autenticados.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Lista de todas as ocorrências",
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
            @ApiResponse(responseCode = "401", description = "Não autorizado")
        }
    )
    @GetMapping
    public ResponseEntity<List<OccurrenceResponseDTO>> getAll(
            @Parameter(hidden = true) HttpServletRequest request
    ) {
        Long agentId = extractUserIdFromRequest(request);
        return ResponseEntity.ok(getAllOccurrencesService.execute());
    }

    @Operation(
        summary = "Consultar ocorrência por ID",
        description = "Retorna os detalhes de uma ocorrência específica pelo seu ID. Disponível apenas para agentes autenticados.",
        parameters = {
            @Parameter(name = "id", description = "ID da ocorrência", required = true, example = "10")
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
            @ApiResponse(responseCode = "404", description = "Ocorrência não encontrada"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<OccurrenceResponseDTO> getById(
            @Parameter(hidden = true) HttpServletRequest request,
            @PathVariable Long id
    ) {
        Long agentId = extractUserIdFromRequest(request);
        return ResponseEntity.ok(getOccurrenceByIdService.execute(id));
    }

    @Operation(
        summary = "Listar ocorrências por endereço",
        description = "Retorna todas as ocorrências que possuem um endereço específico. Disponível apenas para agentes autenticados.",
        parameters = {
            @Parameter(name = "address", description = "Endereço para busca", required = true, example = "Praça Central, 123")
        },
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Lista de ocorrências para o endereço informado",
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
            @ApiResponse(responseCode = "401", description = "Não autorizado")
        }
    )
    @GetMapping("/address/{address}")
    public ResponseEntity<List<OccurrenceResponseDTO>> getByAddress(
            @Parameter(hidden = true) HttpServletRequest request,
            @PathVariable String address
    ) {
        Long agentId = extractUserIdFromRequest(request);
        return ResponseEntity.ok(getOccurrencesByAddressService.execute(address));
    }

    @Operation(
        summary = "Listar ocorrências por categoria",
        description = "Retorna todas as ocorrências de uma determinada categoria. Disponível apenas para agentes autenticados.",
        parameters = {
            @Parameter(name = "category", description = "Nome da categoria", required = true, example = "FURTO")
        },
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Lista de ocorrências para a categoria informada",
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
            @ApiResponse(responseCode = "401", description = "Não autorizado")
        }
    )
    @GetMapping("/category/{category}")
    public ResponseEntity<List<OccurrenceResponseDTO>> getByCategory(
            @Parameter(hidden = true) HttpServletRequest request,
            @PathVariable String category
    ) {
        Long agentId = extractUserIdFromRequest(request);
        return ResponseEntity.ok(getOccurrencesByCategoryService.execute(category));
    }

    @Operation(
        summary = "Atualizar status de ocorrência",
        description = "Permite que o agente atualize o status de uma ocorrência específica.",
        parameters = {
            @Parameter(name = "id", description = "ID da ocorrência", required = true, example = "10")
        },
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = OccurrenceUpdateDTO.class),
                examples = @ExampleObject(
                    name = "Exemplo de requisição",
                    value = """
                    {
                      "status": "RESOLVIDA"
                    }
                    """
                )
            )
        ),
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Status atualizado com sucesso",
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
                          "status": "RESOLVIDA",
                          "category": "FURTO",
                          "photoUrl": null,
                          "createdAt": "2025-05-28T12:00:00",
                          "updatedAt": "2025-05-29T10:30:00"
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
    @PatchMapping("/{id}/status")
    public ResponseEntity<OccurrenceResponseDTO> updateStatus(
            @Parameter(hidden = true) HttpServletRequest request,
            @PathVariable Long id,
            @RequestBody OccurrenceUpdateDTO dto
    ) {
        Long agentId = extractUserIdFromRequest(request);
        return ResponseEntity.ok(updateOccurrenceStatusService.execute(id, dto));
    }
}
