package es.deusto.sd.strava.facade;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api")
@Tag(name = "API de Simulación de Strava", description = "Gestión de usuarios, entrenamientos y retos")
public class StravaController {
    private static final Logger logger = LoggerFactory.getLogger(StravaController.class);
    @Autowired
    private StravaService stravaService;

    @Autowired
    private UsuarioService usuarioService;

    // FUNCION PARA CREAR UNA SESION DE ENTRENAMIENTO EN USUARIO
    @Operation(summary = "Crear un nuevo entrenamiento", description = "Permite crear una nueva sesión de entrenamiento de un usuario", responses = {
            @ApiResponse(responseCode = "200", description = "Entrenamiento creado exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error interno en el servidor"),
            @ApiResponse(responseCode = "401", description = "Usuario no autorizado")
    })

    @PostMapping("/entrenamiento")
    public ResponseEntity<String> crearEntrenamiento(
            @Parameter(name = "titulo", description = "Nombre del entrenamiento a crear", required = true, example = "Entrenamiento1")
            @RequestParam("titulo") String titulo ,
            @Parameter(name = "deporte", description = "Deporte del entrenamiento a crear", required = true, example = "Ciclismo")
            @RequestParam("deporte") String deporte,
            @Parameter(name = "distancia", description = "Distancia del entrenamiento a crear", required = true, example = "10.5")
            @RequestParam("distancia") float distancia,
            @Parameter(name = "duracion", description = "Duración del entrenamiento a crear", required = true, example = "60")
            @RequestParam("duracion") int duracion,
            @Parameter(name = "fechaInicio", description = "Fecha de inicio del entrenamiento a crear", required = true, example = "12/12/2021")
            @RequestParam("fechaInicio") String fechaInicio,
            @Parameter(name = "horaInicio", description = "Hora de inicio del entrenamiento a crear", required = true, example = "12:00")
            @RequestParam("horaInicio") String horaInicio,
            @Parameter(name= "token", description = "Token de autorizacion", required = true, example = "1234567890")
    		@RequestParam("token") String token) {

        Entrenamiento entrenamiento = new Entrenamiento(titulo, deporte, distancia, duracion, fechaInicio, horaInicio);
        Usuario usuario = usuarioService.usuarioPorToken(token);
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(stravaService.crearEntrenamiento(entrenamiento, usuario));
    }

    // DEVUELVE LISTA DE SESIONES DE ENTRENAMIENTOS DE USUARIO
    @Operation(summary = "Consultar todos los entrenamientos del usuario",
     description = "Devuelve la lista completa de entrenamientos realizados por el usuario",
     responses = {
            @ApiResponse(responseCode = "200", description = "Lista de entrenamientos consultada exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error interno en el servidor"),
            @ApiResponse(responseCode = "409", description = "Usuario no existe"),
            @ApiResponse(responseCode = "401", description = "Usuario no autorizado")
    })
    
    @GetMapping("/entrenamientos/{fechaInicio}/{fechaFin}")
    public ResponseEntity<List<EntrenamientoDTO>> consultarEntrenamientos(
            @Parameter(name= "token", description = "Token de autorizacion", required = true, example = "1234567890") 
    		@RequestParam("token") String token,
            @Parameter(name = "fechaInicio", description = "Fecha de inicio para filtrar los entrenamientos", example = "12/12/2021")
            @PathVariable("fechaInicio") String fechaInicio,
            @Parameter(name = "fechaFin", description = "Fecha de fin para filtrar los entrenamientos", example = "12/12/2022")
            @PathVariable("fechaFin") String fechaFin) {

    Usuario usuario = usuarioService.usuarioPorToken(token);
    if (usuario == null) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

     try {
        // Formateador para fechas con el formato dd/MM/yyyy
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        logger.info("Fecha inicio: " + fechaInicio);
        logger.info("Fecha fin: " + fechaFin);

        // Parseo de las fechas de inicio y fin
        LocalDate inicio = LocalDate.parse(fechaInicio, formatter);
        LocalDate fin = LocalDate.parse(fechaFin, formatter);

        // Consultar los entrenamientos en el rango
        List<Entrenamiento> entrenamientos = stravaService.consultarEntrenamientos(usuario, inicio, fin);

        // Convertir a DTOs
        List<EntrenamientoDTO> entrenamientosDTO = new ArrayList<>();
        for (Entrenamiento entrenamiento : entrenamientos) {
            entrenamientosDTO.add(entrenamientoaDTO(entrenamiento));
        }
        return ResponseEntity.ok(entrenamientosDTO);
    } catch (DateTimeParseException e) {
        // Manejo de error si las fechas no son válidas
        logger.info("Fecha inicio: " + fechaInicio);
        logger.info("Fecha fin: " + fechaFin);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
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
    public ResponseEntity<String> crearReto(
            @Parameter(name = "nombre", description = "Nombre del reto a crear", required = true, example = "Reto1")
            @RequestParam("nombre") String nombre,
            @Parameter(name = "deporte", description = "Deporte del reto a crear", required = true, example = "Ciclismo")
            @RequestParam("deporte") String deporte,
            @Parameter(name = "objetivoDistancia", description = "Distancia objetivo del reto a crear", required = true, example = "100")
            @RequestParam("objetivoDistancia") float objetivoDistancia,
            @Parameter(name = "objetivoTiempo", description = "Tiempo objetivo del reto a crear", required = true, example = "60")
            @RequestParam("objetivoTiempo") int objetivoTiempo,
            @Parameter(name = "fechaInicio", description = "Fecha de inicio del reto a crear", required = true, example = "12/12/2021")
            @RequestParam("fechaInicio") String fechaInicio,
            @Parameter(name = "fechaFin", description = "Fecha de fin del reto a crear", required = true, example = "12/12/2021")
            @RequestParam("fechaFin") String fechaFin
            ) {
        Reto reto = new Reto(nombre, deporte, objetivoDistancia, objetivoTiempo, fechaInicio, fechaFin);
        return ResponseEntity.ok(stravaService.crearReto(reto));
    }

    // DEVUELVE LISTA DE RETOS ACTIVOS CREADOS POR LA COMUNIDAD (5 AL INICIO)
    @Operation(summary = "Consultar todos los retos", description = "Devuelve la lista completa de retos creados",
    responses = {
        @ApiResponse(responseCode = "400", description = "Usuario no puede ser nulo"),
        @ApiResponse(responseCode = "500", description = "Error interno en el servidor"),
        @ApiResponse(responseCode = "200", description = "Lista de retos consultada exitosamente")})

    @GetMapping("/retos")
    public ResponseEntity<List<RetoDTO>> consultarRetos() {
        List<Reto> retos = stravaService.consultarRetos();
        List<RetoDTO> retosDTO = new ArrayList<>();
        if (retos != null) {
            for (Reto reto : retos) {
                retosDTO.add(retoaDTO(reto));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(retosDTO);
    }


    @Operation(
        summary = "Consultar todos los retos activos",
        description = "Devuelve los retos activos que no han finalizado, con la opción de obtener los 5 más recientes o filtrarlos por fecha o deporte.",
        parameters = {
            @Parameter(
                name = "fecha",
                description = "Fecha para filtrar los retos por fecha de inicio",
                required = false,
                example = "2024-11-01",
                in = ParameterIn.QUERY
            ),
            @Parameter(
                name = "deporte",
                description = "Deporte para filtrar los retos por tipo de deporte",
                required = false,
                example = "ciclismo",
                in = ParameterIn.QUERY
            )
        },
        responses = {
            @ApiResponse(responseCode = "200", description = "Lista de retos activos consultada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Parámetros incorrectos"),
            @ApiResponse(responseCode = "500", description = "Error interno en el servidor")
        }
    )

    @GetMapping("/retosActivos")
    public ResponseEntity<List<RetoDTO>> consultarRetosActivos(
            @RequestParam(value = "fecha", required = false) String fecha,
            @RequestParam(value = "deporte", required = false) String deporte) {

        try {
            List<Reto> retos = stravaService.consultarRetosActivos(fecha, deporte);
            List<RetoDTO> retosDTO= new ArrayList<>();
            for (Reto reto : retos) {
                retosDTO.add(retoaDTO(reto));
            }
            
            if (retos.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            return ResponseEntity.ok(retosDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
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
    public ResponseEntity<String> aceptarReto(
            @Parameter(name = "nombreReto" ,description = "Nombre del reto a aceptar", required = true, example = "Reto1")
            @PathVariable("nombreReto") String nombreReto,
            @Parameter( name= "token", description = "Token de autorizacion", required = true, example = "1234567890")
            @RequestBody String token) {
        Usuario usuario = usuarioService.usuarioPorToken(token);
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(stravaService.aceptarReto(nombreReto, usuario));
    }

    // FUNCION PARA OBTENER LOS RETOS ACEPTADOS POR EL USUARIO Y SU PROGRESO
    @Operation(summary = "Consultar retos aceptados", description = "Devuelve la lista de retos aceptados por el usuario y su progreso", responses = {
            @ApiResponse(responseCode = "200", description = "Lista de retos aceptados consultada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Usuario no puede ser nulo"),
            @ApiResponse(responseCode = "500", description = "Error interno en el servidor"),
            @ApiResponse(responseCode = "409", description = "Usuario no existe")
    })
    @GetMapping("/retosAceptados")
    public ResponseEntity<List<RetoDTO>>  consultarRetosAceptados(
        @Parameter(name= "token", description = "Token de autorizacion", required = true, example = "1234567890")
        @RequestParam("token") String token) {
        Usuario usuario = usuarioService.usuarioPorToken(token);
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        List<Reto> retos = stravaService.consultarRetosAceptados(usuario);
        List<RetoDTO> retosDTO = new ArrayList<>();
        if (retos != null) {
            for (Reto reto : retos) {
                retosDTO.add(retoaDTO(reto));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(retosDTO);
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
