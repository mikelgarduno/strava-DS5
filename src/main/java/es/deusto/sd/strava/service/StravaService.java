package es.deusto.sd.strava.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import es.deusto.sd.strava.entity.Entrenamiento;
import es.deusto.sd.strava.entity.Reto;
import es.deusto.sd.strava.entity.Usuario;

@Service
public class StravaService {
    private List<Reto> retos = new ArrayList<>();
    
    //FUNCION PARA CREAR UNA SESIÓN DE ENTRENAMIENTO EN USUARIO
    public String crearEntrenamiento(Entrenamiento entrenamiento, Usuario usuario) {
        if(entrenamiento != null) {
            usuario.getEntrenamientos().add(entrenamiento);
            return "Entrenamiento creado exitosamente";
        }
        return "Entrenamiento no puede ser nulo";
    }
    

    // Get all entrenamientos
    public List<Entrenamiento> consultarEntrenamientos() {
        //return entrenamientoRepository.values().stream().toList();
        return null;
    }

    // Get all retos
    public List<Reto> consultarRetos() {
        //return retoRepository.values().stream().toList();
        return retos;
    }



    /*public String crearReto(Reto r) {
        Reto reto = new Reto();
        reto.setObjetivoDistancia(r.getObjetivoDistancia());
        reto.setObjetivoTiempo(r.getObjetivoTiempo());
        reto.setFechaInicio(r.getFechaInicio());
        reto.setFechaFin(r.getFechaFin());
        reto.setDeporte(r.getDeporte());
        reto.setNombre(r.getNombre());
        retos.add(reto);
        return "Reto creado exitosamente";
    }*/
    public String crearReto(Reto reto) {
        if(reto != null) {
            return "Reto registrado con éxito"; 
        }
        return "Reto no puede ser nulo";
    }


    public String aceptarReto(String nombreReto) {
        return "retoAceptado";
    }


    public List<Reto> consultarRetosAceptados(Usuario usuario) {
        return usuario.getRetosAceptados();
    }
}