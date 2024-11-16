package es.deusto.sd.strava.facade;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.deusto.sd.strava.entity.TipoLogin;
import es.deusto.sd.strava.entity.Usuario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import es.deusto.sd.strava.service.UsuarioService;
import es.deusto.sd.strava.dto.UsuarioDTO;

@RestController
@RequestMapping("/auth")
@Tag(name = "Control de los usuarios", description = "Funciones relacionadas con los usuarios: registro, login y logout")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;


    //FUNCION PARA REGISTRAR UN USUARIO
    @Operation(
        summary = "Registrar un nuevo usuario",
        description = "Permite registrar un nuevo usuario en la aplicación mediante los servicios de Google o Meta.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Usuario registrado exitosamente"),
            @ApiResponse(responseCode = "409", description = "El usuario ya existe"),
            @ApiResponse(responseCode = "500", description = "Error interno en el servidor"),
            @ApiResponse(responseCode = "401", description = "Las credenciales no son correctas")
        })

    @PostMapping("/registroUsuario")
    public ResponseEntity<String> registrarUsuario( //Anotaciones swagger y parametros de entrada de la funcion
        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Contraseña, email y método de registro del usuario", required = true)
        @RequestBody UsuarioDTO credenciales,
        @Parameter(name = "nombre", description = "Nombre del usuario a registrar", required = true, example = "Juan12")
        @RequestParam("nombre" ) String nombre,
        @Parameter(name = "fechaNacimiento", description = "Fecha de nacimiento del usuario",required = false, example = "12/12/1990")
        @RequestParam("fechaNacimiento") String fechaNacimiento,
        @Parameter(name = "peso", description = "Peso del usuario a registrar en kilogramos",required = false, example = "19")
        @RequestParam("peso") float peso,
        @Parameter(name = "altura", description = "Altura del usuario a registrar en centimetros",required = false, example = "190")
        @RequestParam("altura") float altura,
        @Parameter(name = "frecuenciaCardiacaMax", description = "Frecuencia cardiaca maxima del usuario a registrar",required = false, example = "140")
        @RequestParam("frecuenciaCardiacaMax") int frecuenciaCardiacaMax,
        @Parameter(name = "frecuenciaCardiacaReposo", description = "Frecuencia cardiaca e<n reposo del usuario a registrar",required = false, example = "70")
        @RequestParam("frecuenciaCardiacaReposo") int frecuenciaCardiacaReposo
        ) {
            if (usuarioService.existeUsuario(credenciales.getEmail())) {
                return new ResponseEntity<>("El usuario con el correo: " + credenciales.getEmail() + " ya existe",
                        HttpStatus.CONFLICT);
            } else {
                try {
                    if (usuarioService.esRegistable(credenciales.getEmail(), credenciales.getContrasenya(),
                            credenciales.getTipoLogin())) {
                        Usuario usuario = new Usuario(nombre, credenciales.getEmail(), peso, altura, fechaNacimiento, frecuenciaCardiacaMax, frecuenciaCardiacaReposo, credenciales.getTipoLogin());
                        usuarioService.añadirUsuario(usuario);
                        return new ResponseEntity<>("El usuario: \"" + nombre + "\" con email: \"" + credenciales.getEmail() + "\" registrado exitosamente", HttpStatus.OK);
                    } else {
                        return new ResponseEntity<>("La contrasenya o el email no son correctos",
                                HttpStatus.UNAUTHORIZED);
                    }
                } catch (Exception e) {
                    return ResponseEntity.status(500).build();
                }
            }
        }

    
    //FUNCION PARA HACER LOGIN
    @Operation(
        summary = "Logearse en el sistema",
        description = "Permite a un usuario iniciar sesión proporcionando correo electrónico y contraseña. Devuelve un token si es exitoso.",
        responses = {
            @ApiResponse(responseCode = "200", description = "OK: Inicio de sesión exitoso, devuelve un token"),
            @ApiResponse(responseCode = "401", description = "No autorizado: Credenciales inválidas, inicio de sesión fallido"),
        }
    )

    @PostMapping("/login")
    public ResponseEntity<String> login(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Contraseña e email del usuario", required = true)
        @RequestBody UsuarioDTO credenciales) 
        {    
        Optional<String> token = usuarioService.login(credenciales.getEmail(), credenciales.getContrasenya());
    	if (token.isPresent()) {
    		return new ResponseEntity<>(token.get(), HttpStatus.OK);
    	} else {
    		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    	}
    }

    // FUNCION PARA HACER LOGOUT
    @Operation(
        summary = "Cerrar sesión del sistema",
        description = "Permite a un usuario cerrar sesión proporcionando el token de autorización.",
        responses = {
            @ApiResponse(responseCode = "204", description = "Sin contenido: Cierre de sesión exitoso"),
            @ApiResponse(responseCode = "401", description = "No autorizado: Token inválido, cierre de sesión fallido"),
        }
    )    
    @PostMapping("/logout")    
    public ResponseEntity<Void> logout(
            @Parameter(name = "token", description = "Token de autorización", required = true, example = "24.433.553.55533.3")
            @RequestBody String token) {
        Optional<Boolean> result = usuarioService.logout(token);
    	
        if (result.isPresent() && result.get()) {
        	return new ResponseEntity<>(HttpStatus.NO_CONTENT);	
        } else {
        	return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }        
    }

    //SOLO SIRVE PARA EL ADMIN, OBTENER USUARIOS
    @Operation(
        summary = "Consultar todos los usuarios",
        description = "Devuelve la lista completa de usuarios registrados",
        responses = {
        @ApiResponse(responseCode = "200", description = "Lista de usuarios consultada exitosamente"),
        @ApiResponse(responseCode = "204", description = "No hay usuarios registrados"),
        @ApiResponse(responseCode = "500", description = "Error interno en el servidor")
    })
    @GetMapping("/usuarios")
    public ResponseEntity<List<Usuario>> consultarUsuarios() {
        try{
            List<Usuario> usuarios = usuarioService.consultarUsuarios();
            if(usuarios.isEmpty()){
                return ResponseEntity.noContent().build();
            }
            return new ResponseEntity<>(usuarioService.consultarUsuarios(), HttpStatus.OK);
        } catch (Exception e){
            return ResponseEntity.status(500).build();
        }
    }
}

 