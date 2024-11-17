package es.deusto.sd.strava.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import es.deusto.sd.strava.entity.Entrenamiento;
import es.deusto.sd.strava.entity.Reto;
import es.deusto.sd.strava.entity.Usuario;

@Service
public class StravaService {
    List<Reto> listaRetos = new ArrayList<>();

    // FUNCION PARA CREAR UNA SESIÓN DE ENTRENAMIENTO EN USUARIO
    public String crearEntrenamiento(Entrenamiento entrenamiento, Usuario usuario) {
        usuario.getEntrenamientos().add(entrenamiento);
        return "El entremaniento \"" + entrenamiento.getTitulo() + "\" ha sido registrado con éxito";
    }

    // OBTENER TODOS LOS ENTRENAMIENTOS DE UN USUARIO
    public List<Entrenamiento> consultarEntrenamientos(Usuario usuario, LocalDate fechaInicio, LocalDate fechaFin) {
        // Validar que el usuario tiene entrenamientos
        if (usuario.getEntrenamientos().isEmpty()) {
            return new ArrayList<>(); // Retorna lista vacía si no hay entrenamientos
        }
        // Filtrar entrenamientos por rango de fechas
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        List<Entrenamiento> entrenamientosFiltrados = new ArrayList<>();

        for (Entrenamiento entrenamiento : usuario.getEntrenamientos()) {
            LocalDate fechaEntrenamiento = LocalDate.parse(entrenamiento.getFechaInicio(), formatter);

            if (!fechaEntrenamiento.isBefore(fechaInicio) && !fechaEntrenamiento.isAfter(fechaFin)) {
                entrenamientosFiltrados.add(entrenamiento);
            }
        }

        // Ordenar por fecha descendente
        entrenamientosFiltrados.sort((e1, e2) -> {
            LocalDate fecha1 = LocalDate.parse(e1.getFechaInicio(), formatter);
            LocalDate fecha2 = LocalDate.parse(e2.getFechaInicio(), formatter);
            return fecha2.compareTo(fecha1); // Descendente
        });
        // Limitar a los 5 más recientes
        if (entrenamientosFiltrados.size() > 5) {
            return entrenamientosFiltrados.subList(0, 5);
        }

        return entrenamientosFiltrados;
    }

    // OBTENER TODOS LOS RETOS
    public List<Reto> consultarRetos() {
        if (listaRetos.isEmpty()) {
            return null;
        } else {
            return listaRetos;
        }
    }

    public List<Reto> consultarRetosActivos(String fecha, String deporte) {
        // Filtrar retos activos que no han finalizado
        LocalDate fechaHoy = LocalDate.now();
        List<Reto> retosActivos = new ArrayList<>();
        for (Reto reto : listaRetos) {
            LocalDate fechaFin = LocalDate.parse(reto.getFechaFin());
            if (fechaFin.isAfter(fechaHoy)) {
                retosActivos.add(reto);
            }
        }

        // Filtrar por fecha si se proporciona
        if (fecha != null && !fecha.isEmpty()) {
            LocalDate fechaFiltro = LocalDate.parse(fecha);
            retosActivos = retosActivos.stream()
                    .filter(reto -> LocalDate.parse(reto.getFechaInicio()).isEqual(fechaFiltro)
                            || LocalDate.parse(reto.getFechaInicio()).isAfter(fechaFiltro))
                    .collect(Collectors.toList());
        }

        // Filtrar por deporte si se proporciona
        if (deporte != null && !deporte.isEmpty()) {
            retosActivos = retosActivos.stream()
                    .filter(reto -> reto.getDeporte().equalsIgnoreCase(deporte))
                    .collect(Collectors.toList());
        }

        // Devolver solo los 5 retos más recientes
        return retosActivos.stream()
                .sorted(Comparator.comparing(Reto::getFechaInicio).reversed()) // Ordenar por fecha de inicio,
                                                                               // descendente
                .limit(5) // Solo los 5 más recientes
                .collect(Collectors.toList());
    }

    public String crearReto(Reto reto) {
        if (reto != null) {
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
        if (usuario.getRetosAceptados().isEmpty()) {
            return null;
        } else {
            return usuario.getRetosAceptados();
        }

    }

}