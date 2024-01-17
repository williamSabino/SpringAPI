package br.com.william.screenmatch.principal;

import br.com.william.screenmatch.models.Series;
import br.com.william.screenmatch.models.Temporada;
import br.com.william.screenmatch.services.ConsumoDeApi;
import br.com.william.screenmatch.services.ConverterJSON;

import java.util.*;

public class Principal {
    List<Temporada> listaTemporadas = new ArrayList<>();
    Scanner scan = new Scanner(System.in);
    //CLASSES
    private ConsumoDeApi consumoDeApi = new ConsumoDeApi();
    private ConverterJSON converterJSON = new ConverterJSON();
    private Series serie;

    //VARIAVEL CONSTANTE
    private final String URL= "https://www.omdbapi.com/?t=";
    private final String API_KEY= "&apikey=7dff1819";

    public void exibirMenu (){
        System.out.println("Digite o nome da serie");
        String busca = scan.nextLine();
        String json = consumoDeApi.consumirApi(URL + busca.replace(" ", "+") + API_KEY);
        this.serie = converterJSON.converterDados(json, Series.class);
        System.out.println(this.serie);
    }

    public void exibirTemporadas(){
        for (int i =1; i<this.serie.totalDeTemporadas();i++){
           String json = consumoDeApi.consumirApi("https://www.omdbapi.com/?t=game+of+thrones&season=" + i + "&apikey=7dff1819");
            Temporada temporada = converterJSON.converterDados(json, Temporada.class);
            listaTemporadas.add(temporada);
        }
    };

}
