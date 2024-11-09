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

    // Simulating a user repository
    private static Map<String, Usuario> userRepository = new HashMap<>();
    
    // Storage to keep the session of the users that are logged in
    private static Map<String, Usuario> tokenStore = new HashMap<>(); 


    // Login method that checks if the user exists in the database and validates the password
    public Optional<String> login(String email, String password) {
        Usuario user = userRepository.get(email);
        
        if (user != null && GoogleMetaService.checkPassword(email, password)) {
            String token = GoogleMetaService.login(email, password);  // Generate a random token for the session
            tokenStore.put(token, user);     // Store the token and associate it with the user

            return Optional.of(token);
        } else {
        	return Optional.empty();
        }
    }
    
    // Logout method to remove the token from the session store
    public Optional<Boolean> logout(String token) {
        if (tokenStore.containsKey(token)) {
            tokenStore.remove(token);

            return Optional.of(true);
        } else {
            return Optional.empty();
        }
    }
    
    // Method to add a new user to the repository
    public void addUser(Usuario user) {
    	if (user != null) {
    		userRepository.putIfAbsent(user.getEmail(), user);
    	}
    }
    
    // Method to get the user based on the token
    public Usuario getUserByToken(String token) {
        return tokenStore.get(token); 
    }
    
    // Method to get the user based on the email
    public Usuario getUserByEmail(String email) {
		return userRepository.get(email);
	}

    // Synchronized method to guarantee unique token generation
    public String obtenerToken(String email, String password) {
        return GoogleMetaService.login(email, password);
    }

    // Get all users
    public List<Usuario> consultarUsuarios() {
        return userRepository.values().stream().toList();
    }
}