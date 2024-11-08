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

    public void cargarDatosMuestra() {
        Usuario usuario1 = new Usuario();
        usuario1.setNombre("Juan Perez");
        usuario1.setEmail("juan.perez@example.com");
        usuario1.setFechaNacimiento("1990-01-01");
        usuario1.setPeso(70);
        usuario1.setAltura(175);
        usuarios.add(usuario1);

        Usuario usuario2 = new Usuario();
        usuario2.setNombre("Maria Lopez");
        usuario2.setEmail("maria.lopez@example.com");
        usuario2.setFechaNacimiento("1985-05-15");
        usuario2.setPeso(60);
        usuario2.setAltura(165);
        usuarios.add(usuario2);

        Usuario usuario3 = new Usuario();
        usuario3.setNombre("Carlos Garcia");
        usuario3.setEmail("carlos.garcia@example.com");
        usuario3.setFechaNacimiento("1992-08-20");
        usuario3.setPeso(80);
        usuario3.setAltura(180);
        usuarios.add(usuario3);
    }

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