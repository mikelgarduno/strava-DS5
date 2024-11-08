package es.deusto.sd.strava.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale.Category;
import java.util.Map;

import org.springframework.stereotype.Service;

import es.deusto.sd.strava.dto.EntrenamientoDTO;
import es.deusto.sd.strava.entity.Entrenamiento;
import es.deusto.sd.strava.entity.Reto;
import es.deusto.sd.strava.entity.Usuario;

@Service
public class StravaService {

    private List<Usuario> usuarios = new ArrayList<>();
    private List<Entrenamiento> entrenamientos = new ArrayList<>();
    private List<Reto> retos = new ArrayList<>();

    public boolean registrarUsuario(Usuario u) {
        Usuario usuario = new Usuario();
        usuario.setNombre(usuario.getNombre());
        usuario.setEmail(usuario.getEmail());
        usuario.setFechaNacimiento(usuario.getFechaNacimiento());
        usuario.setPeso(usuario.getPeso());
        usuario.setAltura(usuario.getAltura());
        usuarios.add(usuario);
        return true;
    }

    // Simulating entrenamiento and reto repositories
	private static Map<Long, Entrenamiento> entrenamientoRepository = new HashMap<>();
    private static Map<Long, Reto> retoRepository = new HashMap();

    // Get all entrenamientos
    public List<Entrenamiento> consultarEntrenamientos() {
        return entrenamientoRepository.values().stream().toList();
    }

    // Get all retos
    public List<Reto> consultarRetos() {
        return retoRepository.values().stream().toList();
    }

    // Create a new entrenamiento
    public String crearEntrenamiento(EntrenamientoDTO entrenamientoDTO) {
        Entrenamiento entrenamiento = new Entrenamiento();
        entrenamiento.setDistancia(entrenamientoDTO.getDistancia());
        entrenamiento.setDuracion(entrenamientoDTO.getDuracion());
        entrenamiento.setFecha(entrenamientoDTO.getFecha());
        entrenamiento.setUsuario(entrenamientoDTO.getUsuario());
        entrenamientoRepository.put(entrenamiento.getId(), entrenamiento);
        return "Entrenamiento creado exitosamente";
    }

    public String crearReto(Reto reto) {
        retos.add(reto);
        return "Reto creado exitosamente";
    }
}