package es.deusto.sd.strava;

import java.util.Calendar;
import java.util.Date;

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
			
			// Create some categories
			Category electronics = new Category("Electronics");
			Category sports = new Category("Sporting Goods");
			Category motors = new Category("Motors");
			
			auctionsService.addCategory(electronics);
			auctionsService.addCategory(sports);
			auctionsService.addCategory(motors);
			logger.info("Categories saved!");


			// Initialize auctions end date
			Calendar calendar = Calendar.getInstance();
			calendar.set(2024, Calendar.DECEMBER, 31);
			Date auctionEndDate = calendar.getTime();
			
			// Articles of Electronics category
            Article iphone = new Article(0, "Apple iPhone 14 Pro", 999.99f, auctionEndDate, electronics, batman);
            Article ps5 = new Article(1, "Sony PlayStation 5", 499.99f, auctionEndDate, electronics, spiderman);
            Article macbook = new Article(2, "MacBook Air M2", 1199.99f, auctionEndDate, electronics, wonderWoman);
            Article samsung = new Article(3, "Samsung Galaxy S21", 799.99f, auctionEndDate, electronics, captainMarvel);
            // Articles of Sporting Goods category
            Article tennisRacket = new Article(4, "Wilson Tennis Racket", 119.99f, auctionEndDate, sports, batman);
            Article soccerBall = new Article(5, "Adidas Soccer Ball", 29.99f, auctionEndDate, sports, blackWidow);
            Article fitbit = new Article(6, "Fitbit Charge 5 Fitness Tracker", 149.99f, auctionEndDate, sports, captainMarvel);
            Article peloton = new Article(7, "Peloton Exercise Bike", 1899.99f, auctionEndDate, sports, wonderWoman);
            // Articles of Motors category
            Article tesla = new Article(8, "Tesla Model 3", 42999.99f, auctionEndDate, motors, batman);
            Article civic = new Article(9, "Honda Civic 2021", 21999.99f, auctionEndDate, motors, superman);
            Article f150 = new Article(10, "Ford F-150 Pickup Truck", 33999.99f, auctionEndDate, motors, spiderman);
            Article corvette = new Article(11, "Chevrolet Corvette Stingray", 59999.99f, auctionEndDate, motors, captainMarvel);

            auctionsService.addArticle(iphone);
            auctionsService.addArticle(ps5);
            auctionsService.addArticle(macbook);
            auctionsService.addArticle(samsung);
            auctionsService.addArticle(tennisRacket);
            auctionsService.addArticle(soccerBall);
            auctionsService.addArticle(fitbit);
            auctionsService.addArticle(peloton);
            auctionsService.addArticle(tesla);
            auctionsService.addArticle(civic);
            auctionsService.addArticle(f150);
            auctionsService.addArticle(corvette);
            logger.info("Articles saved!");						
		};
	}
}

 */