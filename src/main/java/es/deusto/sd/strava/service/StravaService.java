package es.deusto.sd.strava.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import es.deusto.sd.strava.entity.Entrenamiento;
import es.deusto.sd.strava.entity.Reto;
import es.deusto.sd.strava.entity.UsuarioS;

@Service
public class StravaService {

    private List<UsuarioS> usuarios = new ArrayList<>();
    private List<Entrenamiento> entrenamientos = new ArrayList<>();
    private List<Reto> retos = new ArrayList<>();

    public String registrarUsuario(UsuarioS usuario) {
        usuarios.add(usuario);
        return "Usuario registrado exitosamente";
    }

    public List<UsuarioS> consultarUsuarios() {
        return usuarios;
    }

    public List<Entrenamiento> consultarEntrenamientos() {
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