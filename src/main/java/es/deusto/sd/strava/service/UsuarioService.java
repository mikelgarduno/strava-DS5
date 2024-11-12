package es.deusto.sd.strava.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import es.deusto.sd.strava.entity.Usuario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UsuarioService {
    // REPOSITORIO DE USUARIOS Y TOKENS
    private static Map<String, Usuario> usuarios = new HashMap<>();
    private static Map<String, Usuario> tokenes = new HashMap<>(); 

    //REGISTRAR USUARIO
    public Boolean esRegistable(String email, String contraseña) {
        return GoogleMetaService.comprobarEmailContrasena(email, contraseña);
    }

    //AÑADIR USUARIO
      public void addUser(Usuario user) {
    	if (user != null) {
    		usuarios.putIfAbsent(user.getEmail(), user);
    	}
    }


    // LOGIN Y GENERAR TOKEN
    public Optional<String> login(String email, String password) {
        Usuario user = usuarios.get(email);
        
        if (user != null && GoogleMetaService.comprobarEmailContrasena(email, password)) {
            String token = GoogleMetaService.loginToken(email, password);  // Generate a random token for the session
            tokenes.put(token, user);     // Store the token and associate it with the user

            return Optional.of(token);
        } else {
        	return Optional.empty();
        }
    }

    
    // LOGOUT Y BORRAR TOKEN
    public Optional<Boolean> logout(String token) {
        if (tokenes.containsKey(token)) {
            tokenes.remove(token);

            return Optional.of(true);
        } else {
            return Optional.empty();
        }
    }
    
    //OBETENER USUARIO POR TOKEN
    public Usuario getUserByToken(String token) {
        return tokenes.get(token); 
    }

    // CONSEGUIR TODOS LOS USUARIOS ADMIN
    public List<Usuario> consultarUsuarios() {
        return usuarios.values().stream().toList();
    }
}