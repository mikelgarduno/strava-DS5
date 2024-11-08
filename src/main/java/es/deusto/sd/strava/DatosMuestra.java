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

			
			stravaService.consultarUsuarios().forEach(usuario -> logger.info(usuario.getNombre()));
		};
	};
}