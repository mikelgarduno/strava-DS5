package es.deusto.sd.strava.entity;

import java.util.Objects;

public class Reto {
    private String nombre;
    private String deporte;
    private float objetivoDistancia;
    private int objetivoTiempo;
    private boolean aceptado;
    private String fechaInicio;
    private String fechaFin;

    public Reto(String nombre, String deporte, float objetivoDistancia, int objetivoTiempo, boolean aceptado,
            String fechaInicio, String fechaFin) {
        this.nombre = nombre;
        this.deporte = deporte;
        this.objetivoDistancia = objetivoDistancia;
        this.objetivoTiempo = objetivoTiempo;
        this.aceptado = aceptado;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDeporte() {
        return deporte;
    }

    public void setDeporte(String deporte) {
        this.deporte = deporte;
    }

    public float getObjetivoDistancia() {
        return objetivoDistancia;
    }

    public void setObjetivoDistancia(float objetivoDistancia) {
        this.objetivoDistancia = objetivoDistancia;
    }

    public int getObjetivoTiempo() {
        return objetivoTiempo;
    }

    public void setObjetivoTiempo(int objetivoTiempo) {
        this.objetivoTiempo = objetivoTiempo;
    }

    public boolean isAceptado() {
        return aceptado;
    }

    public void setAceptado(boolean aceptado) {
        this.aceptado = aceptado;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Reto reto = (Reto) o;
        return Float.compare(reto.objetivoDistancia, objetivoDistancia) == 0 && objetivoTiempo == reto.objetivoTiempo
                && Objects.equals(nombre, reto.nombre) && Objects.equals(deporte, reto.deporte)
                && Objects.equals(fechaInicio, reto.fechaInicio) && Objects.equals(fechaFin, reto.fechaFin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, deporte);
    }

    @Override
    public String toString() {
        return "Reto{" +
                "nombre='" + nombre + '\'' +
                ", deporte='" + deporte + '\'' +
                ", objetivoDistancia=" + objetivoDistancia +
                ", objetivoTiempo=" + objetivoTiempo +
                ", aceptado=" + aceptado +
                '}';
    }
}
