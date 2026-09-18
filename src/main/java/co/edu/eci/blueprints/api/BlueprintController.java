package co.edu.eci.blueprints.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/blueprints")
@Tag(name = "Blueprints", description = "Operaciones sobre planos protegidas por scopes OAuth2")
public class BlueprintController {

    @GetMapping("/{author}")
    @PreAuthorize("hasAuthority('SCOPE_blueprints.read')")
    @Operation(summary = "Consultar planos por autor", description = "Devuelve los planos asociados a un autor. Requiere el scope 'blueprints.read'.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Planos encontrados"),
            @ApiResponse(responseCode = "401", description = "No autenticado (token ausente o inválido)"),
            @ApiResponse(responseCode = "403", description = "Autenticado pero sin el scope 'blueprints.read'")
    })
    public List<Map<String, String>> getByAuthor(@PathVariable String author) {
        return List.of(
                Map.of("id", "b1", "name", "Plano de " + author));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('SCOPE_blueprints.write')")
    @Operation(summary = "Crear un plano", description = "Crea un nuevo plano. Requiere el scope 'blueprints.write'.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Plano creado"),
            @ApiResponse(responseCode = "401", description = "No autenticado (token ausente o inválido)"),
            @ApiResponse(responseCode = "403", description = "Autenticado pero sin el scope 'blueprints.write'")
    })
    public Map<String, String> create(@RequestBody Map<String, String> in) {
        return Map.of("id", "new", "name", in.getOrDefault("name", "nuevo"));
    }
}
