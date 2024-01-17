package br.com.william.screenmatch;

import br.com.william.screenmatch.models.Episodio;
import br.com.william.screenmatch.models.Series;
import br.com.william.screenmatch.models.Temporada;
import br.com.william.screenmatch.principal.Principal;
import br.com.william.screenmatch.services.ConsumoDeApi;
import br.com.william.screenmatch.services.ConverterJSON;
import br.com.william.screenmatch.services.FormatarJSON;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal();
		principal.exibirMenu();
		principal.exibirTemporadas();
	}
}
