package es.deusto.sd.strava.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Usuario {
    private String nombre;
    private String email;
    private float peso;
    private float altura;
    private String fechaNacimiento;
    private int frecuenciaCardiacaMax;
    private int frecuenciaCardiacaReposo;
    private List<Entrenamiento> entrenamientos;
    private List<Reto> retosAceptados;

    public Usuario() {
    }

    public Usuario(String nombre, String email, float peso, float altura, String fechaNacimiento,
            int frecuenciaCardiacaMax, int frecuenciaCardiacaReposo) {
        this.nombre = nombre;
        this.email = email;
        this.peso = peso;
        this.altura = altura;
        this.fechaNacimiento = fechaNacimiento;
        this.frecuenciaCardiacaMax = frecuenciaCardiacaMax;
        this.frecuenciaCardiacaReposo = frecuenciaCardiacaReposo;
        this.entrenamientos = new ArrayList<>();
        this.retosAceptados = new ArrayList<>();
    }

    public Usuario(String nombre, String email, String fechaNacimiento) {
        this.nombre = nombre;
        this.email = email;
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public float getAltura() {
        return altura;
    }

    public void setAltura(float altura) {
        this.altura = altura;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public int getFrecuenciaCardiacaMax() {
        return frecuenciaCardiacaMax;
    }

    public void setFrecuenciaCardiacaMax(int frecuenciaCardiacaMax) {
        this.frecuenciaCardiacaMax = frecuenciaCardiacaMax;
    }

    public int getFrecuenciaCardiacaReposo() {
        return frecuenciaCardiacaReposo;
    }

    public void setFrecuenciaCardiacaReposo(int frecuenciaCardiacaReposo) {
        this.frecuenciaCardiacaReposo = frecuenciaCardiacaReposo;
    }

    public List<Entrenamiento> getEntrenamientos() {
        return entrenamientos;
    }

    public void setEntrenamientos(List<Entrenamiento> entrenamientos) {
        this.entrenamientos = entrenamientos;
    }

    public List<Reto> getRetosAceptados() {
        return retosAceptados;
    }

    public void setRetosAceptados(List<Reto> retosAceptados) {
        this.retosAceptados = retosAceptados;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Usuario usuario = (Usuario) o;
        return Float.compare(usuario.peso, peso) == 0 &&
                Float.compare(usuario.altura, altura) == 0 &&
                nombre.equals(usuario.nombre) &&
                email.equals(usuario.email) &&
                fechaNacimiento.equals(usuario.fechaNacimiento) &&
                frecuenciaCardiacaMax == usuario.frecuenciaCardiacaMax &&
                frecuenciaCardiacaReposo == usuario.frecuenciaCardiacaReposo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, nombre);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", fechaNacimiento='" + fechaNacimiento + '\'';
    }

}
