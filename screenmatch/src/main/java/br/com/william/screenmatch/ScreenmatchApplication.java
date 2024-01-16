package br.com.william.screenmatch;

import br.com.william.screenmatch.models.Series;
import br.com.william.screenmatch.services.ConsumoDeApi;
import br.com.william.screenmatch.services.ConverterJSON;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("hello world Spring !!");
		var consumoDeApi = new ConsumoDeApi();
		var json = consumoDeApi.consumirApi("https://www.omdbapi.com/?t=game+of+thrones&apikey=7dff1819");
		ConverterJSON converterJSON = new ConverterJSON();
		Series series = converterJSON.converterDados(json, Series.class);
		System.out.println(series);
	}
}
