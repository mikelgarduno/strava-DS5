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

    private List<Usuario> usuarios = new ArrayList<>();
    private List<Reto> retos = new ArrayList<>();
    private List<Entrenamiento> entrenamientos = new ArrayList<>();
    

    public boolean registrarUsuario(Usuario u) {
        usuarios.add(u);
        return true;
    }

    public List<Usuario> consultarUsuarios() {
        return usuarios;
    }

    // Simulating entrenamiento and reto repositories
	private static Map<Long, Entrenamiento> entrenamientoRepository = new HashMap<>();
    private static Map<Long, Reto> retoRepository = new HashMap();

    // Get all entrenamientos
    public List<Entrenamiento> consultarEntrenamientos() {
        //return entrenamientoRepository.values().stream().toList();
        return entrenamientos;
    }

    // Get all retos
    public List<Reto> consultarRetos() {
        //return retoRepository.values().stream().toList();
        return retos;
    }

    //Create a new entrenamiento
    /* public String crearEntrenamiento(Entrenamiento e) {
        Entrenamiento entrenamiento = new Entrenamiento();
        entrenamiento.setDistancia(e.getDistancia());
        entrenamiento.setDuracion(e.getDuracion());
        entrenamiento.setFechaInicio(e.getFechaInicio());
        entrenamiento.setHoraInicio(e.getHoraInicio());
        entrenamiento.setDeporte(e.getDeporte());
        entrenamiento.setTitulo(e.getTitulo());
        return "Entrenamiento creado exitosamente";
    }*/

    public String crearEntrenamiento(Entrenamiento entrenamiento) {
        //entrenamientoRepository.put(entrenamiento.getId(), entrenamiento);
        entrenamientos.add(entrenamiento);
        return "Entrenamiento registrado con éxito";
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
        //retoRepository.put(reto.getId(), reto);
        retos.add(reto);
        return "Reto registrado con éxito";
    }


    
}