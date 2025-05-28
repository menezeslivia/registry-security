package com.seguranca_urbana.backend.controllers.admin;

import com.seguranca_urbana.backend.models.dtos.occurrence.OccurrenceRequestDTO;
import com.seguranca_urbana.backend.models.dtos.occurrence.OccurrenceResponseDTO;
import com.seguranca_urbana.backend.models.dtos.occurrence.OccurrenceUpdateDTO;
import com.seguranca_urbana.backend.services.auth.JwtService;
import com.seguranca_urbana.backend.services.user.admin.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody as SwaggerRequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/occurrences")
@AllArgsConstructor
@Tag(name = "Ocorrências (Admin)", description = "Operações administrativas sobre ocorrências urbanas")
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

    @Operation(
        summary = "Criar ocorrência para um usuário",
        description = "O administrador pode criar uma ocorrência associada a qualquer usuário (informando o userId).",
        parameters = {
            @Parameter(name = "userId", description = "ID do usuário a quem a ocorrência será associada", required = true, example = "2")
        },
        requestBody = @SwaggerRequestBody(
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
                          "id": 20,
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
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
        }
    )
    @PostMapping("/{userId}")
    public ResponseEntity<OccurrenceResponseDTO> create(
            @Parameter(hidden = true) HttpServletRequest request,
            @PathVariable Long userId,
            @RequestBody OccurrenceRequestDTO dto
    ) {
        Long adminId = extractAdminIdFromRequest(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createOccurrenceService.execute(userId, dto));
    }

    @Operation(
        summary = "Listar todas as ocorrências",
        description = "Retorna todas as ocorrências cadastradas no sistema.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Lista de ocorrências",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = OccurrenceResponseDTO.class),
                    examples = @ExampleObject(
                        name = "Exemplo de resposta",
                        value = """
                        [
                          {
                            "id": 20,
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
        Long adminId = extractAdminIdFromRequest(request);
        return ResponseEntity.ok(adminGetAllOccurrencesService.execute());
    }

    @Operation(
        summary = "Buscar ocorrência por ID",
        description = "Retorna os detalhes de uma ocorrência específica pelo ID.",
        parameters = {
            @Parameter(name = "id", description = "ID da ocorrência", required = true, example = "20")
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
                          "id": 20,
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
        Long adminId = extractAdminIdFromRequest(request);
        return ResponseEntity.ok(adminGetOccurrenceByIdService.execute(id));
    }

    @Operation(
        summary = "Buscar ocorrências por endereço",
        description = "Retorna todas as ocorrências que possuem o endereço informado.",
        parameters = {
            @Parameter(name = "address", description = "Endereço", required = true, example = "Praça Central, 123")
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
                            "id": 20,
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
        Long adminId = extractAdminIdFromRequest(request);
        return ResponseEntity.ok(adminGetOccurrencesByAddressService.execute(address));
    }

    @Operation(
        summary = "Atualizar ocorrência (todos os campos editáveis)",
        description = "Atualiza os dados de uma ocorrência específica.",
        parameters = {
            @Parameter(name = "id", description = "ID da ocorrência", required = true, example = "20")
        },
        requestBody = @SwaggerRequestBody(
            required = true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = OccurrenceUpdateDTO.class),
                examples = @ExampleObject(
                    name = "Exemplo de atualização",
                    value = """
                    {
                      "description": "Furto de bicicleta preta",
                      "address": "Praça Central, 123"
                    }
                    """
                )
            )
        ),
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Ocorrência atualizada com sucesso",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = OccurrenceResponseDTO.class),
                    examples = @ExampleObject(
                        name = "Exemplo de resposta",
                        value = """
                        {
                          "id": 20,
                          "userId": 2,
                          "address": "Praça Central, 123",
                          "description": "Furto de bicicleta preta",
                          "status": "ABERTA",
                          "category": "FURTO",
                          "photoUrl": null,
                          "createdAt": "2025-05-28T12:00:00",
                          "updatedAt": "2025-05-28T13:45:00"
                        }
                        """
                    )
                )
            ),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Ocorrência não encontrada"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
        }
    )
    @PutMapping("/{id}")
    public ResponseEntity<OccurrenceResponseDTO> update(
            @Parameter(hidden = true) HttpServletRequest request,
            @PathVariable Long id,
            @RequestBody OccurrenceUpdateDTO dto
    ) {
        Long adminId = extractAdminIdFromRequest(request);
        return ResponseEntity.ok(adminUpdateOccurrenceService.execute(id, dto));
    }

    @Operation(
        summary = "Atualizar status da ocorrência",
        description = "Atualiza apenas o status de uma ocorrência específica.",
        parameters = {
            @Parameter(name = "id", description = "ID da ocorrência", required = true, example = "20")
        },
        requestBody = @SwaggerRequestBody(
            required = true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = OccurrenceUpdateDTO.class),
                examples = @ExampleObject(
                    name = "Exemplo de alteração de status",
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
                          "id": 20,
                          "userId": 2,
                          "address": "Praça Central, 123",
                          "description": "Furto de bicicleta preta",
                          "status": "RESOLVIDA",
                          "category": "FURTO",
                          "photoUrl": null,
                          "createdAt": "2025-05-28T12:00:00",
                          "updatedAt": "2025-05-28T14:05:00"
                        }
                        """
                    )
                )
            ),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Ocorrência não encontrada"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
        }
    )
    @PatchMapping("/{id}/status")
    public ResponseEntity<OccurrenceResponseDTO> updateStatus(
            @Parameter(hidden = true) HttpServletRequest request,
            @PathVariable Long id,
            @RequestBody OccurrenceUpdateDTO dto
    ) {
        Long adminId = extractAdminIdFromRequest(request);
        return ResponseEntity.ok(adminUpdateOccurrenceStatusService.execute(id, dto));
    }

    @Operation(
        summary = "Excluir ocorrência",
        description = "Remove definitivamente uma ocorrência do sistema.",
        parameters = {
            @Parameter(name = "id", description = "ID da ocorrência", required = true, example = "20")
        },
        responses = {
            @ApiResponse(responseCode = "204", description = "Ocorrência removida com sucesso"),
            @ApiResponse(responseCode = "404", description = "Ocorrência não encontrada"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
        }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(hidden = true) HttpServletRequest request,
            @PathVariable Long id
    ) {
        Long adminId = extractAdminIdFromRequest(request);
        adminDeleteOccurrenceService.execute(id);
        return ResponseEntity.noContent().build();
    }
}
