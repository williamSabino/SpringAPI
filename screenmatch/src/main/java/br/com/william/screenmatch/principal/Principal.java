package br.com.william.screenmatch.principal;

import br.com.william.screenmatch.models.DadosEpisodio;
import br.com.william.screenmatch.models.DadosSeries;
import br.com.william.screenmatch.models.DadosTemporada;
import br.com.william.screenmatch.models.Episodio;
import br.com.william.screenmatch.services.ConsumoDeApi;
import br.com.william.screenmatch.services.ConverterJSON;
import br.com.william.screenmatch.services.FormatarJSON;
import org.springframework.boot.autoconfigure.web.format.DateTimeFormatters;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private String busca;
    private Scanner scan = new Scanner(System.in);

    private List<DadosTemporada> dadosTemporadas = new ArrayList<>();

    //CLASSES
    private DadosSeries serie;
    private DadosTemporada dadosTemporada;
    private ConsumoDeApi consumoDeApi = new ConsumoDeApi();
    private ConverterJSON converterJSON = new ConverterJSON();

    //CONSTANTES
    private final String URL= "https://www.omdbapi.com/?t=";
    private final String APIKEY = "&apikey=7dff1819";

    public void exibirMenu(){
        System.out.println("Digite o nome da Série : \n");
        this.busca = scan.nextLine();
        String urlCompleta = URL + busca.replaceAll(" ", "+") + APIKEY;
        String json = consumoDeApi.consumirApi(urlCompleta);
        this.serie = converterJSON.converterDados(json, DadosSeries.class);
        System.out.println(this.serie);
    }

    public void exibirTemporadas(){
        for (int i =1; i<=this.serie.totalDeTemporadas();i++){
            String urlBusca = "https://www.omdbapi.com/?t=" + this.busca.replaceAll(" ", "+") + "&season=" + i + "&apikey=7dff1819";
            String json = consumoDeApi.consumirApi(urlBusca);
            dadosTemporada = converterJSON.converterDados(json, DadosTemporada.class);
            dadosTemporadas.add(dadosTemporada);
        }



        System.out.println("Lista de 5 melhores episodios");
        List<Episodio> episodiosLista = dadosTemporadas.stream()
                .flatMap(t -> t.episodios().stream()
                .map(d -> new Episodio(t.temporada(), d))
                )
                .collect(Collectors.toList());

        System.out.println("Os 5 melhores episodios ");
       episodiosLista.stream()
               .sorted(Comparator.comparing(Episodio::getAvaliacao).reversed())
               .limit(5)
               .forEach(System.out::println);

        System.out.println("Busca de episodios por ano, Digite um ano : \n");
        int anoBusca = scan.nextInt();

        LocalDate localDate = LocalDate.of(anoBusca,1,1);

        episodiosLista.stream()
                .filter(e -> e.getDataLancamento() != null && e.getDataLancamento().isAfter(localDate))
                .forEach(System.out::println);
    }





}
