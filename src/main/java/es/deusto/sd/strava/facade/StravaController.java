package es.deusto.sd.strava.facade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.deusto.sd.strava.entity.Entrenamiento;
import es.deusto.sd.strava.entity.Reto;
import es.deusto.sd.strava.entity.UsuarioS;
import es.deusto.sd.strava.service.StravaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/api")
@Tag(name = "API de Simulación de Strava", description = "Gestión de usuarios, entrenamientos y retos")
public class StravaController {

    @Autowired
    private StravaService stravaService;

    @Operation(summary = "Registrar un nuevo usuario",
               description = "Permite registrar un nuevo usuario en la aplicación")
    @ApiResponse(responseCode = "200", description = "Usuario registrado exitosamente")
    @PostMapping("/usuarios")
    public String registrarUsuario(@RequestBody UsuarioS usuario) {
        return stravaService.registrarUsuario(usuario);
    }

    @Operation(summary = "Consultar todos los usuarios",
               description = "Devuelve la lista completa de usuarios registrados")
    @ApiResponse(responseCode = "200", description = "Lista de usuarios consultada exitosamente")
    @GetMapping("/usuarios")
    public List<UsuarioS> consultarUsuarios() {
        return stravaService.consultarUsuarios();
    }

    @Operation(summary = "Consultar todos los entrenamientos",
               description = "Devuelve la lista completa de entrenamientos realizados")
    @ApiResponse(responseCode = "200", description = "Lista de entrenamientos consultada exitosamente")
    @GetMapping("/entrenamientos")
    public List<Entrenamiento> consultarEntrenamientos() {
        return stravaService.consultarEntrenamientos();
    }

    @Operation(summary = "Consultar todos los retos",
               description = "Devuelve la lista completa de retos creados")
    @ApiResponse(responseCode = "200", description = "Lista de retos consultada exitosamente")
    @GetMapping("/retos")
    public List<Reto> consultarRetos() {
        return stravaService.consultarRetos();
    }

    @Operation(summary = "Crear un nuevo entrenamiento",
               description = "Permite crear un nuevo entrenamiento")
    @ApiResponse(responseCode = "200", description = "Entrenamiento creado exitosamente")
    @PostMapping("/entrenamientos")
    public String crearEntrenamiento(
            @Parameter(description = "Datos del entrenamiento a crear") @RequestBody Entrenamiento entrenamiento) {
        return stravaService.crearEntrenamiento(entrenamiento);
    }

    @Operation(summary = "Crear un nuevo reto",
               description = "Permite crear un nuevo reto")
    @ApiResponse(responseCode = "200", description = "Reto creado exitosamente")
    @PostMapping("/retos")
    public String crearReto(
            @Parameter(description = "Datos del reto a crear") @RequestBody Reto reto) {
        return stravaService.crearReto(reto);
    }
}
