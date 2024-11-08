package es.deusto.sd.strava;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.deusto.sd.strava.entity.Entrenamiento;
import es.deusto.sd.strava.entity.Reto;
import es.deusto.sd.strava.entity.Usuario;
import es.deusto.sd.strava.service.StravaService;

@Configuration
public class DatosMuestra {

	private static final Logger logger = LoggerFactory.getLogger(DatosMuestra.class);
	
    @Bean
    CommandLineRunner initData(StravaService stravaService) {
		return args -> {			
			Usuario usainBolt = new Usuario("UsainBolt", "usain.bolt@athletics.com", "12-13-1978");
			Usuario michaelPhelps = new Usuario("MichaelPhelps", "michael.phelps@swimming.com", "06-30-1985");
			Usuario serenaWilliams = new Usuario("SerenaWilliams", "serena.williams@tennis.com", "09-26-1981");
			Usuario lionelMessi = new Usuario("LionelMessi", "lionel.messi@soccer.com", "06-24-1987");
			Usuario lebronJames = new Usuario("LeBronJames", "lebron.james@basketball.com", "12-30-1984");
			Usuario cristianoRonaldo = new Usuario("CristianoRonaldo", "cristiano.ronald@soccer.com", 80.0f, 1.87f, "02-05-1985", 220, 60);	

			stravaService.registrarUsuario(usainBolt);
			stravaService.registrarUsuario(michaelPhelps);
			stravaService.registrarUsuario(serenaWilliams);
			stravaService.registrarUsuario(lionelMessi);
			stravaService.registrarUsuario(lebronJames);
			stravaService.registrarUsuario(cristianoRonaldo);
			logger.info("Usuarios registrados!");

			 // Retos de muestra
			Reto maraton = new Reto("Maratón Ciudad", "Correr", 42.195f, 300, false, "2024-01-01", "2024-01-31");
			Reto triatlon = new Reto("Triatlón Olímpico", "Nadar-Correr-Ciclismo", 51.5f, 240, false, "2024-02-01", "2024-02-28");
			 
			stravaService.crearReto(maraton);
			stravaService.crearReto(triatlon);
			logger.info("Retos registrados!");
	 
			// Entrenamientos de muestra
			Entrenamiento carreraMañana = new Entrenamiento("Carrera Matutina", "Correr", 5.0f, 30, "2024-01-02", "08:00");
			Entrenamiento ciclismoRuta = new Entrenamiento("Ruta en Bicicleta", "Ciclismo", 20.0f, 60, "2024-01-03", "10:00");
			 
			stravaService.crearEntrenamiento(carreraMañana);
			stravaService.crearEntrenamiento(ciclismoRuta);
			logger.info("Entrenamientos registrados!");

		};
	};
}