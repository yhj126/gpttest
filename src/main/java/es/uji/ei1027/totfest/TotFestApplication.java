package es.uji.ei1027.totfest;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.util.logging.Logger;

@SpringBootApplication
public class TotFestApplication implements CommandLineRunner {

	private static final Logger log = Logger.getLogger(TotFestApplication .class.getName());

	public static void main(String[] args) {
		new SpringApplicationBuilder(TotFestApplication.class).run(args);
	}

	// Funció principal
	public void run(String... strings) throws Exception {
		log.info("Servidor lanzado");
	}
}
