package es.deusto.sd.strava.entity;

public class Reto {
    private String nombre;
    private String deporte;
    private float objetivoDistancia;
    private int objetivoTiempo;
    private boolean aceptado;

    public Reto(String nombre, String deporte, float objetivoDistancia, int objetivoTiempo, boolean aceptado) {
        this.nombre = nombre;
        this.deporte = deporte;
        this.objetivoDistancia = objetivoDistancia;
        this.objetivoTiempo = objetivoTiempo;
        this.aceptado = aceptado;
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
