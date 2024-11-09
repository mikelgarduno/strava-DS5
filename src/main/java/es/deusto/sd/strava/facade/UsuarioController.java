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
import org.springframework.web.bind.annotation.RestController;

import es.deusto.sd.strava.entity.Usuario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import es.deusto.sd.strava.service.StravaService;
import es.deusto.sd.strava.service.UsuarioService;

@RestController
@RequestMapping("/usuario")
@Tag(name = "Controlador de los usuarios", description = "Funciones relacionadas con los usuarios: registro, login y logout")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;


    //FUNCION PARA REGISTRAR UN USUARIO
    @Operation(
        summary = "Registrar un nuevo usuario",
        description = "Permite registrar un nuevo usuario en la aplicación",
        responses = {
            @ApiResponse(responseCode = "200", description = "Usuario registrado exitosamente"),
            @ApiResponse(responseCode = "409", description = "El usuario ya existe"),
            @ApiResponse(responseCode = "500", description = "Error interno en el servidor")
        })
        
    @PostMapping("/usuarios")
    public boolean registrarUsuario(@RequestBody Usuario usuario) {
        return true;
    }

    
    //FUNCION PARA HACER LOGIN
    @Operation(
        summary = "Login to the system",
        description = "Allows a user to login by providing email and password. Returns a token if successful.",
        responses = {
            @ApiResponse(responseCode = "200", description = "OK: Login successful, returns a token"),
            @ApiResponse(responseCode = "401", description = "Unauthorized: Invalid credentials, login failed"),
        }
    )
    @PostMapping("/login")
    public ResponseEntity<String> login(
    		@Parameter(name = "credentials", description = "User's credentials", required = true)    	
    		@RequestBody  String email, String contraseña ) {    	
        Optional<String> token = usuarioService.login(email, contraseña);
        
    	if (token.isPresent()) {
    		return new ResponseEntity<>(token.get(), HttpStatus.OK);
    	} else {
    		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    	}
    }

    // FUNCION PARA HACER LOGOUT
    @Operation(
        summary = "Logout from the system",
        description = "Allows a user to logout by providing the authorization token.",
        responses = {
            @ApiResponse(responseCode = "204", description = "No Content: Logout successful"),
            @ApiResponse(responseCode = "401", description = "Unauthorized: Invalid token, logout failed"),
        }
    )    
    @PostMapping("/logout")    
    public ResponseEntity<Void> logout(
    		@Parameter(name = "token", description = "Authorization token", required = true, example = "Bearer 1924888a05c")
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

 