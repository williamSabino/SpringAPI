package br.com.william.screenmatch;

import br.com.william.screenmatch.models.Episodio;
import br.com.william.screenmatch.models.Series;
import br.com.william.screenmatch.models.Temporada;
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
		List<String> listaEpisodios = new ArrayList<>();
		var converterJSON = new ConverterJSON();
		var consumoDeApi = new ConsumoDeApi();
		var formatarJson = new FormatarJSON();
		String jsonConvertido;
		String json;

		json = consumoDeApi.consumirApi("https://www.omdbapi.com/?t=game+of+thrones&apikey=7dff1819");
		Series series = converterJSON.converterDados(json, Series.class);
		jsonConvertido = formatarJson.converterJSON(series);
		System.out.println("Serie \n" + jsonConvertido);

		json = consumoDeApi.consumirApi("https://www.omdbapi.com/?t=game+of+thrones&season=1&episode=1&apikey=7dff1819");
		Episodio episodio = converterJSON.converterDados(json, Episodio.class);
		jsonConvertido = formatarJson.converterJSON(episodio);
		System.out.println("Episodio \n" + jsonConvertido);


		for (int i =0; i<series.totalDeTemporadas();i++){
			json = consumoDeApi.consumirApi("https://www.omdbapi.com/?t=game+of+thrones&season=" + i + "&apikey=7dff1819");
			Temporada temporada = converterJSON.converterDados(json, Temporada.class);
			jsonConvertido = formatarJson.converterJSON(temporada);
			listaEpisodios.add(jsonConvertido);
		}
		System.out.println("********************************** lista ************************************");
		System.out.println(listaEpisodios);
	}
}
