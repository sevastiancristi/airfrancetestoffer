package com.airfrance.testoffer;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {
	private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

	@SuppressWarnings("deprecation")
	@Bean
	CommandLineRunner initDatabase(UserRepository repository) {

		return args -> {
//			log.info("Preloading " + repository
//					.save(Mockito.any(User.class)));
			log.info("Preloading " + repository
					.save(new User("dsad", new SimpleDateFormat("yyyy-MM-dd").parse("2002-01-01"), "France",null,null)));
			log.info("Preloading " + repository
					.save(new User("asewwqd", new SimpleDateFormat("yyyy-MM-dd").parse("2001-01-01"), "France",null,null)));
		};
	}
}
