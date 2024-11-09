package es.deusto.sd.strava.service;

import org.springframework.stereotype.Service;
import es.deusto.sd.strava.entity.Usuario;

@Service
public class GoogleMetaService {
    
    //METODO QUE DEVUELVE UN TOKEN ALEATORIO SI EL CORREO Y EL EMAIL EXISTEN EN LA BD
    public static synchronized String login(String email, String password) {
        return Long.toHexString(System.currentTimeMillis());
    }

    //METODO QUE DEVUELVE UN BOOLEANO SI LA CONTRASEÑA DEL USUARIO ES CORRECTA
    public static boolean checkPassword(String email, String password) {
        return true;
    }
}
