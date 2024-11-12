package es.deusto.sd.strava.dto;

public class UsuarioDTO {
    String contrasenya;
    String email;

    public UsuarioDTO() {
    }

    public UsuarioDTO(String contrasenya, String email) {
        this.contrasenya = contrasenya;
        this.email = email;
    }

    public String getContrasenya() {
        return contrasenya;
    }

    public void setContrasenya(String contrasenya) {
        this.contrasenya = contrasenya;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
