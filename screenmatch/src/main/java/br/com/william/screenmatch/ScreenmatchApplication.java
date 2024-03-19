package br.com.william.screenmatch;

import br.com.william.screenmatch.principal.Principal;
import br.com.william.screenmatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class ScreenmatchApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}


}
