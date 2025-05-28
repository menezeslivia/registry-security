package com.seguranca_urbana.backend.controllers.admin;

import com.seguranca_urbana.backend.models.dtos.user.UserRequestDTO;
import com.seguranca_urbana.backend.models.dtos.user.UserResponseDTO;
import com.seguranca_urbana.backend.models.dtos.user.UserUpdateDTO;
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
@RequestMapping("/admin/users")
@AllArgsConstructor
@Tag(name = "Usuários (Admin)", description = "Operações administrativas sobre usuários do sistema")
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

    @Operation(
        summary = "Criar novo usuário",
        description = "O administrador pode criar um novo usuário do sistema.",
        requestBody = @SwaggerRequestBody(
            required = true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = UserRequestDTO.class),
                examples = @ExampleObject(
                    name = "Exemplo de requisição",
                    value = """
                    {
                      "username": "maria",
                      "password": "12345678",
                      "role": "AGENT"
                    }
                    """
                )
            )
        ),
        responses = {
            @ApiResponse(
                responseCode = "201",
                description = "Usuário criado com sucesso",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserResponseDTO.class),
                    examples = @ExampleObject(
                        name = "Exemplo de resposta",
                        value = """
                        {
                          "id": 6,
                          "username": "maria",
                          "role": "AGENT"
                        }
                        """
                    )
                )
            ),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
        }
    )
    @PostMapping
    public ResponseEntity<UserResponseDTO> create(
            @Parameter(hidden = true) HttpServletRequest request,
            @RequestBody UserRequestDTO dto
    ) {
        Long adminId = extractUserIdFromRequest(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createUserService.execute(dto));
    }

    @Operation(
        summary = "Listar todos os usuários",
        description = "Retorna todos os usuários cadastrados no sistema.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Lista de usuários",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserResponseDTO.class),
                    examples = @ExampleObject(
                        name = "Exemplo de resposta",
                        value = """
                        [
                          {
                            "id": 6,
                            "username": "maria",
                            "role": "AGENT"
                          },
                          {
                            "id": 7,
                            "username": "joao",
                            "role": "COMMONER"
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
    public ResponseEntity<List<UserResponseDTO>> getAll(
            @Parameter(hidden = true) HttpServletRequest request
    ) {
        Long adminId = extractUserIdFromRequest(request);
        return ResponseEntity.ok(adminGetAllUsersService.execute());
    }

    @Operation(
        summary = "Buscar usuário por ID",
        description = "Retorna os detalhes de um usuário específico pelo ID.",
        parameters = {
            @Parameter(name = "id", description = "ID do usuário", required = true, example = "6")
        },
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Usuário encontrado",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserResponseDTO.class),
                    examples = @ExampleObject(
                        name = "Exemplo de resposta",
                        value = """
                        {
                          "id": 6,
                          "username": "maria",
                          "role": "AGENT"
                        }
                        """
                    )
                )
            ),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getById(
            @Parameter(hidden = true) HttpServletRequest request,
            @PathVariable Long id
    ) {
        Long adminId = extractUserIdFromRequest(request);
        return ResponseEntity.ok(adminGetUserByIdService.execute(id));
    }

    @Operation(
        summary = "Buscar usuário por username",
        description = "Retorna os detalhes de um usuário pesquisando pelo username.",
        parameters = {
            @Parameter(name = "username", description = "Username do usuário", required = true, example = "maria")
        },
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Usuário encontrado",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserResponseDTO.class),
                    examples = @ExampleObject(
                        name = "Exemplo de resposta",
                        value = """
                        {
                          "id": 6,
                          "username": "maria",
                          "role": "AGENT"
                        }
                        """
                    )
                )
            ),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
        }
    )
    @GetMapping("/username/{username}")
    public ResponseEntity<UserResponseDTO> getByUsername(
            @Parameter(hidden = true) HttpServletRequest request,
            @PathVariable String username
    ) {
        Long adminId = extractUserIdFromRequest(request);
        return ResponseEntity.ok(adminGetUserByUsernameService.execute(username));
    }

    @Operation(
        summary = "Atualizar um usuário",
        description = "Atualiza os dados de um usuário específico.",
        parameters = {
            @Parameter(name = "id", description = "ID do usuário", required = true, example = "6")
        },
        requestBody = @SwaggerRequestBody(
            required = true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = UserUpdateDTO.class),
                examples = @ExampleObject(
                    name = "Exemplo de atualização",
                    value = """
                    {
                      "username": "maria.silva",
                      "password": "novaSenha123",
                      "role": "ADMIN"
                    }
                    """
                )
            )
        ),
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Usuário atualizado com sucesso",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserResponseDTO.class),
                    examples = @ExampleObject(
                        name = "Exemplo de resposta",
                        value = """
                        {
                          "id": 6,
                          "username": "maria.silva",
                          "role": "ADMIN"
                        }
                        """
                    )
                )
            ),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
        }
    )
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> update(
            @Parameter(hidden = true) HttpServletRequest request,
            @PathVariable Long id,
            @RequestBody UserUpdateDTO dto
    ) {
        Long adminId = extractUserIdFromRequest(request);
        return ResponseEntity.ok(adminUpdateUserService.execute(id, dto));
    }

    @Operation(
        summary = "Excluir usuário",
        description = "Remove definitivamente um usuário do sistema.",
        parameters = {
            @Parameter(name = "id", description = "ID do usuário", required = true, example = "6")
        },
        responses = {
            @ApiResponse(responseCode = "204", description = "Usuário removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
        }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(hidden = true) HttpServletRequest request,
            @PathVariable Long id
    ) {
        Long adminId = extractUserIdFromRequest(request);
        adminDeleteUserService.execute(id);
        return ResponseEntity.noContent().build();
    }
}
