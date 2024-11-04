package es.deusto.sd.strava.entity;

public class Usuario {
    private String nombre;
    private String email;
    private float peso;
    private float altura;

    public Usuario() {
    }

    public Usuario(String nombre, String email, float peso, float altura) {
        this.nombre = nombre;
        this.email = email;
        this.peso = peso;
        this.altura = altura;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Float.compare(usuario.peso, peso) == 0 &&
                Float.compare(usuario.altura, altura) == 0 &&
                nombre.equals(usuario.nombre) &&
                email.equals(usuario.email);
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", peso=" + peso +
                ", altura=" + altura +
                '}';
    }

    

}
