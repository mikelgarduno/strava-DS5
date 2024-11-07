package es.deusto.sd.strava.dto;

public class EntrenamientoDTO {
    private String titulo;
    private String deporte;
    private float distancia;
    private int duracion;
    private String fechaInicio;
    private String horaInicio;

    public EntrenamientoDTO() {
    }

    public EntrenamientoDTO(String titulo, String deporte, float distancia, int duracion, String fechaInicio,
            String horaInicio) {
        this.titulo = titulo;
        this.deporte = deporte;
        this.distancia = distancia;
        this.duracion = duracion;
        this.fechaInicio = fechaInicio;
        this.horaInicio = horaInicio;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDeporte() {
        return deporte;
    }

    public void setDeporte(String deporte) {
        this.deporte = deporte;
    }

    public float getDistancia() {
        return distancia;
    }

    public void setDistancia(float distancia) {
        this.distancia = distancia;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }
}
