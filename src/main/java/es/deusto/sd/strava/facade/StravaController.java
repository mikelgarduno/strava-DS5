package es.deusto.sd.strava.facade;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.deusto.sd.strava.dto.RetoDTO;
import es.deusto.sd.strava.dto.EntrenamientoDTO;
import es.deusto.sd.strava.entity.Entrenamiento;
import es.deusto.sd.strava.entity.Reto;
import es.deusto.sd.strava.entity.Usuario;
import es.deusto.sd.strava.service.StravaService;
import es.deusto.sd.strava.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api")
@Tag(name = "API de Simulación de Strava", description = "Gestión de usuarios, entrenamientos y retos")
public class StravaController {

    @Autowired
    private StravaService stravaService;

    @Autowired
    private UsuarioService usuarioService;

    // FUNCION PARA CREAR UNA SESION DE ENTRENAMIENTO EN USUARIO
    @Operation(summary = "Crear un nuevo entrenamiento", description = "Permite crear una nueva sesión de entrenamiento de un usuario", responses = {
            @ApiResponse(responseCode = "200", description = "Entrenamiento creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Entrenamiento no puede ser nulo"),
            @ApiResponse(responseCode = "500", description = "Error interno en el servidor"),
            @ApiResponse(responseCode = "409", description = "Entrenamiento ya existe")
    })

    @PostMapping("/entrenamiento")
    public String crearEntrenamiento(
            @Parameter(name = "titulo", description = "Nombre del entrenamiento a crear", required = true, example = "Entrenamiento1")
            @RequestParam String titulo ,
            @Parameter(name = "deporte", description = "Deporte del entrenamiento a crear", required = true, example = "Ciclismo")
            @RequestParam String deporte,
            @Parameter(name = "distancia", description = "Distancia del entrenamiento a crear", required = true, example = "10.5")
            @RequestParam float distancia,
            @Parameter(name = "duracion", description = "Duración del entrenamiento a crear", required = true, example = "60")
            @RequestParam int duracion,
            @Parameter(name = "fechaInicio", description = "Fecha de inicio del entrenamiento a crear", required = true, example = "12/12/2021")
            @RequestParam String fechaInicio,
            @Parameter(name = "horaInicio", description = "Hora de inicio del entrenamiento a crear", required = true, example = "12:00")
            @RequestParam String horaInicio,
            @Parameter( description = "Token de autorizacion", required = true, example = "1234567890")
    		@RequestBody String token) {

        Entrenamiento entrenamiento = new Entrenamiento(titulo, deporte, distancia, duracion, fechaInicio, horaInicio);
        return stravaService.crearEntrenamiento(entrenamiento, usuarioService.usuarioPorToken(token));
    }

    // DEVUELVE LISTA DE SESIONES DE ENTRENAMIENTOS DE USUARIO
    @Operation(summary = "Consultar todos los entrenamientos del usuario",
     description = "Devuelve la lista completa de entrenamientos realizados por el usuario",
     responses = {
            @ApiResponse(responseCode = "200", description = "Lista de entrenamientos consultada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Usuario no puede ser nulo"),
            @ApiResponse(responseCode = "500", description = "Error interno en el servidor"),
            @ApiResponse(responseCode = "409", description = "Usuario no existe")
    })
    
    @GetMapping("/entrenamientos")
    public List<Entrenamiento> consultarEntrenamientos(
            @Parameter(name= "token", description = "Token de autorizacion", required = true, example = "1234567890") 
    		@RequestBody String token
    ) {
        Usuario usuario = usuarioService.usuarioPorToken(token);
        return stravaService.consultarEntrenamientos(usuario);
    }

    // FUNCION PARA CREAR UN RETO
    @Operation(summary = "Crear un nuevo reto", description = "Permite crear un nuevo reto", 
    responses = {
            @ApiResponse(responseCode = "200", description = "Reto creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Reto no puede ser nulo"),
            @ApiResponse(responseCode = "500", description = "Error interno en el servidor"),
            @ApiResponse(responseCode = "409", description = "Reto ya existe")
    })
 
    @PostMapping("/reto")
    public String crearReto(
            @Parameter(name = "nombre", description = "Nombre del reto a crear", required = true, example = "Reto1")
            @RequestParam String nombre,
            @Parameter(name = "deporte", description = "Deporte del reto a crear", required = true, example = "Ciclismo")
            @RequestParam String deporte,
            @Parameter(name = "objetivoDistancia", description = "Distancia objetivo del reto a crear", required = true, example = "100")
            @RequestParam float objetivoDistancia,
            @Parameter(name = "objetivoTiempo", description = "Tiempo objetivo del reto a crear", required = true, example = "60")
            @RequestParam int objetivoTiempo,
            @Parameter(name = "fechaInicio", description = "Fecha de inicio del reto a crear", required = true, example = "12/12/2021")
            @RequestParam String fechaInicio,
            @Parameter(name = "fechaFin", description = "Fecha de fin del reto a crear", required = true, example = "12/12/2021")
            @RequestParam String fechaFin
            ) {
        Reto reto = new Reto(nombre, deporte, objetivoDistancia, objetivoTiempo, fechaInicio, fechaFin);
        return stravaService.crearReto(reto);
    }

    // DEVUELVE LISTA DE RETOS ACTIVOS CREADOS POR LA COMUNIDAD (5 AL INICIO)
    @Operation(summary = "Consultar todos los retos", description = "Devuelve la lista completa de retos creados",
    responses = {
        @ApiResponse(responseCode = "400", description = "Usuario no puede ser nulo"),
        @ApiResponse(responseCode = "500", description = "Error interno en el servidor"),
        @ApiResponse(responseCode = "200", description = "Lista de retos consultada exitosamente")})

    @GetMapping("/retos")
    public List<Reto> consultarRetos() {
        return stravaService.consultarRetos();
    }

    // FUNCION PARA ACEPTAR UN RETO
    @Operation(summary = "Aceptar un reto", description = "Permite aceptar un reto de la comunidad",
         responses = {
            @ApiResponse(responseCode = "200", description = "Reto aceptado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Reto no puede ser nulo"),
            @ApiResponse(responseCode = "500", description = "Error interno en el servidor"),
            @ApiResponse(responseCode = "409", description = "Reto ya aceptado")
    })
    @PostMapping("/retos/{nombreReto}/aceptar")
    public String aceptarReto(
            @Parameter(name = "nombreReto" ,description = "Nombre del reto a aceptar", required = true, example = "Reto1")
            @PathVariable("nombreReto") String nombreReto,
            @Parameter( name= "token", description = "Token de autorizacion", required = true, example = "1234567890")
            @RequestBody String token) {
        Usuario usuario = usuarioService.usuarioPorToken(token);
        return stravaService.aceptarReto(nombreReto, usuario);
    }

    // FUNCION PARA OBTENER LOS RETOS ACEPTADOS POR EL USUARIO Y SU PROGRESO
    @Operation(summary = "Consultar retos aceptados", description = "Devuelve la lista de retos aceptados por el usuario y su progreso", responses = {
            @ApiResponse(responseCode = "200", description = "Lista de retos aceptados consultada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Usuario no puede ser nulo"),
            @ApiResponse(responseCode = "500", description = "Error interno en el servidor"),
            @ApiResponse(responseCode = "409", description = "Usuario no existe")
    })
    @GetMapping("/retosAceptados")
    public List<RetoDTO> consultarRetosAceptados(
            @Parameter(name = "token", description = "Token de autorizacion", required = true, example = "1234567890")
            @RequestBody String token) {
        Usuario usuario = usuarioService.usuarioPorToken(token);
        List<Reto> retos = stravaService.consultarRetosAceptados(usuario);
        List<RetoDTO> retosDTO = new ArrayList<>();
        if (retos != null) {
            for (Reto reto : retos) {
                retosDTO.add(retoaDTO(reto));
            }
        } else {
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
                reto.getFechaInicio(),
                reto.getFechaFin(),
                0);
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
