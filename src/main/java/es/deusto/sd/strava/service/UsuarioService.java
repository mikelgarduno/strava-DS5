package es.deusto.sd.strava.service;


import org.springframework.stereotype.Service;

import es.deusto.sd.strava.entity.TipoLogin;
import es.deusto.sd.strava.entity.Usuario;


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
    public Boolean esRegistable(String email, String contraseña, TipoLogin tipoLogin) {
        if(tipoLogin == TipoLogin.GOOGLE) {
            return GoogleService.comprobarEmailContrasena(email, contraseña);
        } else {
            return MetaService.comprobarEmailContrasena(email, contraseña);
        }
    }

    //AÑADIR USUARIO
      public void añadirUsuario(Usuario user) {
    	if (user != null) {
    		usuarios.putIfAbsent(user.getEmail(), user);
    	}
    }


    // LOGIN Y GENERAR TOKEN
    public Optional<String> login(String email, String password, TipoLogin tipoLogin) {
        Usuario usuario = usuarios.get(email); //Ya hemos comprobado que el usuario existe anteriormente
        if(tipoLogin == TipoLogin.GOOGLE) {
            if (GoogleService.comprobarEmailContrasena(email, password)) {
                String token = GoogleService.loginToken(email, password);
                tokenes.put(token, usuario);    
                return Optional.of(token);
            } else {
                return Optional.empty();
            }
        } else {
            if (MetaService.comprobarEmailContrasena(email, password)) {
                String token = MetaService.loginToken(email, password);  
                tokenes.put(token, usuario);     
    
                return Optional.of(token);
            } else {
                return Optional.empty();
            }
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
    
    //OBTENER USUARIO POR TOKEN
    public Usuario usuarioPorToken(String token) {
        return tokenes.get(token); 
    }

    //OBTENER USUARIO POR EMAIL
    public Optional<Usuario> usuarioPorEmail(String email) {
        if (!usuarios.containsKey(email)) {
            return Optional.empty();
        }else {
            return Optional.of(usuarios.get(email));
        }
    }

    //EXISTE USUARIO
    public Boolean existeUsuario(String email) {
        return usuarios.containsKey(email);
    }

    // CONSEGUIR TODOS LOS USUARIOS ADMIN
    public List<Usuario> consultarUsuarios() {
        return usuarios.values().stream().toList();
    }
    

}