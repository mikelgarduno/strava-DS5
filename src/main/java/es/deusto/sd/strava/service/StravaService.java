package es.deusto.sd.strava.service;

import java.util.ArrayList;
import java.util.List;

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

    public List<Usuario> consultarUsuarios() {
        return usuarios;
    }

    public List<Entrenamiento> consultarEntrenamientos() {
        List<Entrenamiento> entrenamientos = new ArrayList<>();
        for (Entrenamiento e : entrenamientos) {
            entrenamientos.add(new Entrenamiento(e.getTitulo(), e.getDeporte(), e.getDistancia(), e.getDuracion(), e.getFechaInicio(), e.getHoraInicio()));
        }
        return entrenamientos;
    }

    public List<Reto> consultarRetos() {
        return retos;
    }

    public String crearEntrenamiento(Entrenamiento entrenamiento) {
        entrenamientos.add(entrenamiento);
        return "Entrenamiento creado exitosamente";
    }

    public String crearReto(Reto reto) {
        retos.add(reto);
        return "Reto creado exitosamente";
    }
}