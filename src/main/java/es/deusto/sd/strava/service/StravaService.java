package es.deusto.sd.strava.service;

import java.time.LocalDate;


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
        List<Entrenamiento> entrenamientosFiltrados = new ArrayList<>();
        for (Entrenamiento entrenamiento : usuario.getEntrenamientos()) {
            LocalDate fechaEntrenamiento = entrenamiento.getFechaInicio();
            if (fechaEntrenamiento.isAfter(fechaInicio) && fechaEntrenamiento.isBefore(fechaFin)) {
                entrenamientosFiltrados.add(entrenamiento);
            }
        }
        // Ordenar los entrenamientos por fecha de inicio de manera descendente
        entrenamientosFiltrados.sort((e1, e2) -> e2.getFechaInicio().compareTo(e1.getFechaInicio()));

        // Limitar la lista a los 5 entrenamientos más recientes
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

    public List<Reto> consultarRetosActivos(LocalDate fecha, String deporte) {
        List<Reto> retosActivos = new ArrayList<>();
        for (Reto reto : listaRetos) {
            if (reto.getFechaFin().isAfter(fecha)) {
                // Si se especifica deporte, filtrar también por deporte
                if (deporte == null || reto.getDeporte().equalsIgnoreCase(deporte)) {
                    retosActivos.add(reto);
                }
            }
        }
        retosActivos.sort((r1, r2) -> r2.getFechaInicio().compareTo(r1.getFechaInicio()));
        if (retosActivos.size() > 5) {
            return retosActivos.subList(0, 5);
        }
        return retosActivos;

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

    //funcion para consultar los retos activos de un usuario junto al progreso de cada uno( segun los entrenamientos que ha realizado durante las fechas del reto)
    public Map<Reto,Integer> consultarRetosActivosConProgreso(Usuario usuario) {
        LocalDate fecha = LocalDate.now();
        Map<Reto,Integer> retosActivos = new HashMap<>();

        if(usuario.getRetosAceptados().isEmpty()){  //Si el usuario no tiene retos aceptados o entrenamientos
            return retosActivos;
        }

        for (Reto reto : usuario.getRetosAceptados()) {  //Recorremos los retos aceptados por el usuario
            int progreso = 0;
            
            if (reto.getFechaFin().isAfter(fecha)) {    //Si el reto no ha finalizado
                
                for (Entrenamiento entrenamiento : usuario.getEntrenamientos()) {  //Recorremos los entrenamientos del usuario
                   
                    if (entrenamiento.getFechaInicio().isAfter(reto.getFechaInicio())   //Si el entrenamiento se ha realizado durante las fechas del reto
                            && entrenamiento.getFechaInicio().isBefore(reto.getFechaFin())) {   
                        
                        if(reto.getObjetivoDistancia() > 0){   //Si el reto es de distancia
                            progreso += entrenamiento.getDistancia();
                        }else{
                            progreso += entrenamiento.getDuracion(); 
                        }
                    }
                }

                int porcentaje = 0;
                if (reto.getObjetivoDistancia() > 0) { //Si el reto es de distancia
                    porcentaje = (int) ((progreso * 100) / reto.getObjetivoDistancia()); //Calculamos el porcentaje de progreso
                }else{
                    porcentaje = (progreso*100)/reto.getObjetivoTiempo(); //Calculamos el porcentaje de progreso
                }
                retosActivos.put(reto, porcentaje); //Añadimos el reto y su progreso al mapa
            }
        }
        return retosActivos;
    }

}