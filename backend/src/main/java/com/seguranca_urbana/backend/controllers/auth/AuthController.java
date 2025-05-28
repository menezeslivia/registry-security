package com.seguranca_urbana.backend.controllers.auth;

import com.seguranca_urbana.backend.models.dtos.auth.AuthResponseDTO;
import com.seguranca_urbana.backend.models.dtos.auth.LoginRequestDTO;
import com.seguranca_urbana.backend.models.dtos.auth.RegisterRequestDTO;
import com.seguranca_urbana.backend.services.auth.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody as SwaggerRequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticação", description = "Rotas públicas para cadastro e login de usuários")
public class AuthController {

    private final AuthService authService;

    @Operation(
        summary = "Registrar novo usuário",
        description = "Realiza o cadastro de um novo usuário no sistema.",
        requestBody = @SwaggerRequestBody(
            required = true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = RegisterRequestDTO.class),
                examples = @ExampleObject(
                    name = "Exemplo de registro",
                    value = """
                    {
                      "username": "livia",
                      "password": "12345678",
                      "role": "ADMIN"
                    }
                    """
                )
            )
        ),
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Usuário registrado com sucesso",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AuthResponseDTO.class),
                    examples = @ExampleObject(
                        name = "Exemplo de resposta de registro",
                        value = """
                        {
                          "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
                          "user": {
                            "id": 5,
                            "username": "livia",
                            "role": "ADMIN"
                          }
                        }
                        """
                    )
                )
            ),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para cadastro")
        }
    )
    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(
            @org.springframework.web.bind.annotation.RequestBody RegisterRequestDTO request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @Operation(
        summary = "Login de usuário",
        description = "Realiza o login do usuário no sistema.",
        requestBody = @SwaggerRequestBody(
            required = true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = LoginRequestDTO.class),
                examples = @ExampleObject(
                    name = "Exemplo de login",
                    value = """
                    {
                      "username": "livia",
                      "password": "12345678"
                    }
                    """
                )
            )
        ),
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Login realizado com sucesso",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AuthResponseDTO.class),
                    examples = @ExampleObject(
                        name = "Exemplo de resposta de login",
                        value = """
                        {
                          "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
                          "user": {
                            "id": 5,
                            "username": "livia",
                            "role": "ADMIN"
                          }
                        }
                        """
                    )
                )
            ),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
        }
    )
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(
            @org.springframework.web.bind.annotation.RequestBody LoginRequestDTO request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
