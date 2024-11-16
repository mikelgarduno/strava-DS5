package es.deusto.sd.strava.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import es.deusto.sd.strava.entity.Entrenamiento;
import es.deusto.sd.strava.entity.Reto;
import es.deusto.sd.strava.entity.Usuario;

@Service
public class StravaService {
    List<Reto> listaRetos = new ArrayList<>();

    //FUNCION PARA CREAR UNA SESIÓN DE ENTRENAMIENTO EN USUARIO
    public String crearEntrenamiento(Entrenamiento entrenamiento, Usuario usuario) {
        if(entrenamiento != null && usuario != null) {
            usuario.getEntrenamientos().add(entrenamiento);
            return "Entrenamiento creado exitosamente";
        } else{
            return "Entrenamiento no puede ser nulo";
        }
    }
    

    // OBTENER TODOS LOS ENTRENAMIENTOS DE UN USUARIO
    public List<Entrenamiento> consultarEntrenamientos(Usuario usuario) {
        if(usuario.getEntrenamientos().isEmpty()) {
            return null;
        } else {
            return usuario.getEntrenamientos();
        }
    }

    // OBTENER TODOS LOS RETOS 
    public List<Reto> consultarRetos() {
        if (listaRetos.isEmpty()) {
            return null;
        } else {
            return listaRetos;
        }
    }

    public String crearReto(Reto reto) {
        if(reto != null) {
            listaRetos.add(reto);
            return "Reto registrado con éxito"; 
        } else {
            return "Reto no puede ser nulo";
        }
    }


    public String aceptarReto(String nombreReto, Usuario usuario) {
        if (nombreReto != null && usuario != null) {
            for (Reto reto : listaRetos) {
                if (reto.getNombre().equals(nombreReto)) {
                    usuario.getRetosAceptados().add(reto);
                    return "Reto aceptado con éxito";
                }
            }
            return "Reto no encontrado";
        } else {
            return "El nombre del reto y el usuario no pueden ser nulos";
        }
        
    }

    public List<Reto> consultarRetosAceptados(Usuario usuario) {
        if(usuario.getRetosAceptados().isEmpty()) {
            return null;
        } else {
            return usuario.getRetosAceptados();
        }
        
    }

    
}