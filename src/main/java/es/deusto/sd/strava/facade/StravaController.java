package es.deusto.sd.strava.facade;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.deusto.sd.strava.dto.RetoDTO;
import es.deusto.sd.strava.dto.EntrenamientoDTO;
import es.deusto.sd.strava.entity.Entrenamiento;
import es.deusto.sd.strava.entity.Reto;
import es.deusto.sd.strava.entity.Usuario;
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

    // FUNCION PARA CREAR UNA SESION DE ENTRENAMIENTO EN USUARIO
    @Operation(summary = "Crear un nuevo entrenamiento", description = "Permite crear una nueva sesión de entrenamiento de un usuario", responses = {
            @ApiResponse(responseCode = "200", description = "Entrenamiento creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Entrenamiento no puede ser nulo"),
            @ApiResponse(responseCode = "500", description = "Error interno en el servidor"),
            @ApiResponse(responseCode = "409", description = "Entrenamiento ya existe")
    })

    @PostMapping("/entrenamiento")
    public String crearEntrenamiento(
            @Parameter(description = "Datos del entrenamiento a crear") @RequestBody Entrenamiento entrenamiento) {
        return stravaService.crearEntrenamiento(entrenamiento, null);
    }

    // DEVUELVE LISTA DE SESIONES DE ENTRENAMIENTOS DE USUARIO
    @Operation(summary = "Consultar todos los entrenamientos", description = "Devuelve la lista completa de entrenamientos realizados")
    @ApiResponse(responseCode = "200", description = "Lista de entrenamientos consultada exitosamente")
    @GetMapping("/entrenamientos")
    public List<Entrenamiento> consultarEntrenamientos() {
        return stravaService.consultarEntrenamientos();
    }

    // FUNCION PARA CREAR UN RETO
    @Operation(summary = "Crear un nuevo reto", description = "Permite crear un nuevo reto")
    @ApiResponse(responseCode = "200", description = "Reto creado exitosamente")
    @PostMapping("/reto")
    public String crearReto(
            @Parameter(description = "Datos del reto a crear") @RequestBody Reto reto) {
        return stravaService.crearReto(reto);
    }

    // DEVUELVE LISTA DE RETOS ACTIVOS CREADOS POR LA COMUNIDAD (5 AL INICIO)
    @Operation(summary = "Consultar todos los retos", description = "Devuelve la lista completa de retos creados")
    @ApiResponse(responseCode = "200", description = "Lista de retos consultada exitosamente")
    @GetMapping("/retos")
    public List<Reto> consultarRetos() {
        return stravaService.consultarRetos();
    }

    // FUNCION PARA ACEPTAR UN RETO
    @Operation(summary = "Aceptar un reto", description = "Permite aceptar un reto de la comunidad", responses = {
            @ApiResponse(responseCode = "200", description = "Reto aceptado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Reto no puede ser nulo"),
            @ApiResponse(responseCode = "500", description = "Error interno en el servidor"),
            @ApiResponse(responseCode = "409", description = "Reto ya aceptado")
    })
    @PostMapping("/retos/{nombreReto}/aceptar")
    public String aceptarReto(
            @Parameter(description = "Nombre del reto a aceptar") String nombreReto) {
        return stravaService.aceptarReto(nombreReto);
    }

    // FUNCION PARA OBTENER LOS RETOS ACEPTADOS POR EL USUARIO Y SU PROGRESO
    @Operation(summary = "Consultar retos aceptados", description = "Devuelve la lista de retos aceptados por el usuario y su progreso", responses = {
            @ApiResponse(responseCode = "200", description = "Lista de retos aceptados consultada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Usuario no puede ser nulo"),
            @ApiResponse(responseCode = "500", description = "Error interno en el servidor"),
            @ApiResponse(responseCode = "409", description = "Usuario no existe")
    })
    @GetMapping("/retos/{usuario}/aceptados")
    public List<RetoDTO> consultarRetosAceptados(
            @Parameter(name = "usuario", description = "Nombre del usuario", required = true, example = "pepe")
            @PathVariable("usuario") String usuario) {
        Usuario usuarioX = new Usuario(usuario, null, null);
        List<Reto> retos = stravaService.consultarRetosAceptados(usuarioX);
        List<RetoDTO> retosDTO = new ArrayList<>();
        if(retos != null) {
            for (Reto reto : retos) {
                retosDTO.add(retoaDTO(reto));
            }
        }else{
            return null;
        }
        return retosDTO;
    }

    // FUNCION PARA PASAR DE RETO A RETO DTO
    private RetoDTO retoaDTO(Reto reto) {
        return new RetoDTO(reto.getNombre(),
                reto.getDeporte(),
                reto.getObjetivoDistancia(),
                reto.getObjetivoTiempo(),
                reto.isAceptado(),
                reto.getFechaInicio(),
                reto.getFechaFin());
    }

    // FUNCION PARA PASAR DE ENTRENAMIENTO A ENTRENAMIENTO DTO
    private EntrenamientoDTO entrenamientoaDTO(Entrenamiento entrenamiento) {
        return new EntrenamientoDTO(entrenamiento.getTitulo(),
                entrenamiento.getDeporte(),
                entrenamiento.getDistancia(),
                entrenamiento.getDuracion(),
                entrenamiento.getFechaInicio(),
                entrenamiento.getHoraInicio());
    }

}
