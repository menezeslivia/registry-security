package com.seguranca_urbana.backend.controllers.admin;

import com.seguranca_urbana.backend.models.dtos.occurrence.OccurrenceCategoryRequestDTO;
import com.seguranca_urbana.backend.models.dtos.occurrence.OccurrenceCategoryResponseDTO;
import com.seguranca_urbana.backend.models.dtos.occurrence.OccurrenceCategoryUpdateDTO;
import com.seguranca_urbana.backend.services.occurrence.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/categories")
@Tag(name = "Categorias de Ocorrência (Admin)", description = "Operações para administração das categorias de ocorrência")
public class AdminCategoryController {

    @Autowired
    private CreateCategoryService createCategoryService;
    @Autowired
    private UpdateCategoryService updateCategoryService;
    @Autowired
    private DeleteCategoryService deleteCategoryService;
    @Autowired
    private GetAllCategoriesService getAllCategoriesService;
    @Autowired
    private GetCategoryByIdService getCategoryByIdService;

    @Operation(
        summary = "Criar nova categoria de ocorrência",
        description = "Cria uma nova categoria de ocorrência urbana. Apenas administradores podem acessar.",
        requestBody = @RequestBody(
            required = true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = OccurrenceCategoryRequestDTO.class),
                examples = @ExampleObject(
                    name = "Exemplo de requisição",
                    value = """
                    {
                      "name": "FURTO",
                      "description": "Furto de bens móveis ou imóveis"
                    }
                    """
                )
            )
        ),
        responses = {
            @ApiResponse(
                responseCode = "201",
                description = "Categoria criada com sucesso",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = OccurrenceCategoryResponseDTO.class),
                    examples = @ExampleObject(
                        name = "Exemplo de resposta",
                        value = """
                        {
                          "id": 1,
                          "name": "FURTO",
                          "description": "Furto de bens móveis ou imóveis"
                        }
                        """
                    )
                )
            ),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
        }
    )
    @PostMapping
    public ResponseEntity<OccurrenceCategoryResponseDTO> create(@RequestBody OccurrenceCategoryRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(createCategoryService.execute(dto));
    }

    @Operation(
        summary = "Listar todas as categorias de ocorrência",
        description = "Retorna todas as categorias cadastradas no sistema.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Lista de categorias",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = OccurrenceCategoryResponseDTO.class),
                    examples = @ExampleObject(
                        name = "Exemplo de resposta",
                        value = """
                        [
                          {
                            "id": 1,
                            "name": "FURTO",
                            "description": "Furto de bens móveis ou imóveis"
                          },
                          {
                            "id": 2,
                            "name": "ROUBO",
                            "description": "Roubo com uso de violência ou ameaça"
                          }
                        ]
                        """
                    )
                )
            )
        }
    )
    @GetMapping
    public ResponseEntity<List<OccurrenceCategoryResponseDTO>> getAll() {
        return ResponseEntity.ok(getAllCategoriesService.execute());
    }

    @Operation(
        summary = "Buscar categoria de ocorrência por ID",
        description = "Retorna os detalhes de uma categoria específica.",
        parameters = {
            @Parameter(name = "id", description = "ID da categoria", required = true, example = "1")
        },
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Categoria encontrada",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = OccurrenceCategoryResponseDTO.class),
                    examples = @ExampleObject(
                        name = "Exemplo de resposta",
                        value = """
                        {
                          "id": 1,
                          "name": "FURTO",
                          "description": "Furto de bens móveis ou imóveis"
                        }
                        """
                    )
                )
            ),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<OccurrenceCategoryResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(getCategoryByIdService.execute(id));
    }

    @Operation(
        summary = "Atualizar categoria de ocorrência",
        description = "Atualiza uma categoria existente pelo ID.",
        parameters = {
            @Parameter(name = "id", description = "ID da categoria", required = true, example = "1")
        },
        requestBody = @RequestBody(
            required = true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = OccurrenceCategoryUpdateDTO.class),
                examples = @ExampleObject(
                    name = "Exemplo de requisição",
                    value = """
                    {
                      "name": "FURTO QUALIFICADO",
                      "description": "Furto com agravantes"
                    }
                    """
                )
            )
        ),
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Categoria atualizada com sucesso",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = OccurrenceCategoryResponseDTO.class),
                    examples = @ExampleObject(
                        name = "Exemplo de resposta",
                        value = """
                        {
                          "id": 1,
                          "name": "FURTO QUALIFICADO",
                          "description": "Furto com agravantes"
                        }
                        """
                    )
                )
            ),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
        }
    )
    @PutMapping("/{id}")
    public ResponseEntity<OccurrenceCategoryResponseDTO> update(@PathVariable Long id, @RequestBody OccurrenceCategoryUpdateDTO dto) {
        return ResponseEntity.ok(updateCategoryService.execute(id, dto));
    }

    @Operation(
        summary = "Excluir categoria de ocorrência",
        description = "Remove uma categoria de ocorrência pelo ID.",
        parameters = {
            @Parameter(name = "id", description = "ID da categoria", required = true, example = "1")
        },
        responses = {
            @ApiResponse(responseCode = "204", description = "Categoria removida com sucesso"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
        }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deleteCategoryService.execute(id);
        return ResponseEntity.noContent().build();
    }
}
