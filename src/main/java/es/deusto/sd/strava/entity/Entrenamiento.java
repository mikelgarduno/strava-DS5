package es.deusto.sd.strava.entity;

public class Entrenamiento {
    private String titulo;
    private String deporte;
    private float distancia;
    private int duracion;

    public Entrenamiento() {
    }

    public Entrenamiento(String titulo, String deporte, float distancia, int duracion) {
        this.titulo = titulo;
        this.deporte = deporte;
        this.distancia = distancia;
        this.duracion = duracion;
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

    @Override
    public String toString() {
        return "Entrenamiento{" +
                "titulo='" + titulo + '\'' +
                ", deporte='" + deporte + '\'' +
                ", distancia=" + distancia +
                ", duracion=" + duracion +
                '}';
    }
}
