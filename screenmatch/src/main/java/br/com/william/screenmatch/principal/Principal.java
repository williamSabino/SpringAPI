package br.com.william.screenmatch.principal;

import br.com.william.screenmatch.models.*;
import br.com.william.screenmatch.repository.SerieRepository;
import br.com.william.screenmatch.services.ConsumoDeApi;
import br.com.william.screenmatch.services.ConverterJSON;

import java.util.*;

public class Principal {
    List<DadosSeries> listaSeries = new ArrayList<>();
    private Scanner scan = new Scanner(System.in);

    //CLASSES
    private ConsumoDeApi consumoDeApi = new ConsumoDeApi();
    private ConverterJSON converterJSON = new ConverterJSON();

    //CONSTANTES
    private final String URL= "https://www.omdbapi.com/?t=";
    private final String APIKEY = "&apikey=7dff1819";
    private SerieRepository repository;

    private List<Serie> series = new ArrayList<>();
    Optional<Serie> serieEncontrada;
    public Principal(SerieRepository repository) {
        this.repository = repository;
    }

    public void exibirMenu(){
        int opcao;


        do {
            System.out.println("""
                \n Escolha uma das opções 
                01 - Buscar série
                02 - Buscar episodios da série
                03 - Listar Series buscadas
                04 - Deletar Série
                05 - Buscar Série pelo titulo
                06 - Buscar Série pelo ator
                07 - Top 5 Melhores
                08 - Buscar por categoria
                09 - Buscar por temporada e avaliacao
                10 - Buscar por trecho de episodios
                11 - Top 5 melhores Episodios
                00 - Sair \n
                """);
            opcao = scan.nextInt();

            switch (opcao){
                case 1 -> {
                   buscarSerie();
                }
                case 2 ->{
                    buscarEpisodios();
                }
                case 3 ->{
                    listarSeries();
                }
                case 4 -> {
                    deletarSerie();
                }
                case 5 -> {
                    buscarSeriePeloTitulo();
                }
                case 6 -> {
                    buscarSeriePeloAtor();
                }
                case 7 -> {
                    buscarTop5Melhores();
                }
                case 8 -> {
                    buscarSeriePorCategoria();
                }
                case 9 -> {
                    buscarPorNumeroDetemporadasEAvaliacoes();
                }
                case 10 -> {
                    buscaEpisodioPorTrechos();
                }
                case 11 -> {
                    top5MelhoresEpisodios();
                }
                case 12 -> {
                    buscarEpisodiosAposAnoDeLançamento();
                }
                case 0 -> {
                    opcao = 0;
                }
                case default ->{
                    System.out.println("Invalido");
                    opcao = 0;
                }
            }
        }while(opcao != 0);
    } // end exibirMenu



    public void buscarSerie (){
        System.out.println("Digite o nome da Série : \n");
        scan.nextLine();
        String busca = scan.nextLine();
        String urlCompleta = URL + busca.replaceAll(" ", "+") + APIKEY;
        String json = consumoDeApi.consumirApi(urlCompleta);
        DadosSeries dadosserie = converterJSON.converterDados(json, DadosSeries.class);
        Serie serie = new Serie(dadosserie);
        repository.save(serie);
        System.out.println(serie);
    }

    public void buscarEpisodios (){
        listarSeries();
        System.out.println("Digite o nome da série : \n");
        scan.nextLine();
        String busca = scan.nextLine();

        Optional<Serie> serie = series.stream()
                .filter(s -> s.getTitulo().toLowerCase().equals(busca.toLowerCase()))
                .findFirst();

        if(serie.isPresent()){
        List<DadosTemporada> listaTemporadas = new ArrayList<>();
        for (int i = 1; i <= serie.get().getTotalDeTemporadas(); i++) {
            String urlEpisodios = URL + busca.replaceAll(" ", "+")+ "&season="+ i + APIKEY;
            String json = consumoDeApi.consumirApi(urlEpisodios);
            DadosTemporada temporada = converterJSON.converterDados(json, DadosTemporada.class);
            listaTemporadas.add(temporada);
        }
            List<Episodio> episodios = listaTemporadas.stream()
                    .flatMap(d -> d.episodios().stream()
                            .map(e -> new Episodio(d.temporada(), e))).toList();

            serie.get().setEpisodios(episodios);
            repository.save(serie.get());
        } else {
            System.out.println("Série não encontrada");
        }


    }

    private void deletarSerie(){
        listarSeries();
        System.out.println("Digite o nome da série");
        scan.nextLine();
        String busca = scan.nextLine();
        Optional<Serie> serieEncontrada = series.stream()
                .filter(s -> s.getTitulo().toLowerCase().equalsIgnoreCase(busca))
                .findFirst();

        if(serieEncontrada.isPresent()){
            var serie = serieEncontrada.get();
            repository.deleteById(serie.getId());
        }else{
            System.out.println("Série não encontrada");
        }
    }

    private void  listarSeries(){
        series = repository.findAll();
        series.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);
    }

    private void buscarSeriePeloTitulo() {
        System.out.println("Digite o nome da série");
        scan.nextLine();
        String busca = scan.nextLine();

        serieEncontrada = repository.findByTituloContainingIgnoreCase(busca);

        if(serieEncontrada.isPresent()){
            System.out.println("Serie encontrada : "+ serieEncontrada.get());
        }else{
            System.out.println("Série não encontrada");
        }
    }

    private void buscarSeriePeloAtor() {
        System.out.println("Digite o nome do ator");
        scan.nextLine();
        var nomeAtor = scan.nextLine();
        System.out.println("Digite a nota de corte da série");
        var avaliacao = scan.nextDouble();

        List<Serie> seriesEncontradas = repository.findByAtoresContainingIgnoreCaseAndNotaGreaterThanEqual(nomeAtor, avaliacao);

        seriesEncontradas.forEach(s -> {
            System.out.println(s.getTitulo() + "Avaliação : " + s.getNota());
        });
    }

    private void buscarTop5Melhores() {
        List<Serie> top5Series = repository.findTop5ByOrderByNotaDesc();
        System.out.println("\n Top 5 séries mais bem avaliadas : \n");
        top5Series.forEach(s -> {
            System.out.println(s.getTitulo() + "Avaliação : " + s.getNota());
        });
    }

    private void buscarSeriePorCategoria() {
        System.out.println("Digite a categoria");
        scan.nextLine();
        var categoriaDigitada = scan.nextLine();
        Categoria categoria = Categoria.selecionarCategoriaPortugues(categoriaDigitada);
        List<Serie> seriesEncontradas = repository.findByGenero(categoria);
        seriesEncontradas.forEach(s -> {
            System.out.println(s.getTitulo() + ", categoria : " + s.getGenero() + ", Avaliacao : " + s.getNota());
        });
    }


    private void buscarPorNumeroDetemporadasEAvaliacoes() {
        System.out.println("Digite o total de temporadas limite");
        var temporadasLimite = scan.nextInt();
        System.out.println("Digite o numero de corte da avaliação");
        var avaliacaoCorte = scan.nextDouble();
    List<Serie> seriesEncontradas = repository.buscaPorNumeroDeTemporadasEAvaliacao(temporadasLimite, avaliacaoCorte);
        seriesEncontradas.forEach(s -> {
            System.out.println(s.getTitulo() + ", Temporadas : " + s.getTotalDeTemporadas() + ", Avaliacao : " + s.getNota());
        });
    }


    private void buscaEpisodioPorTrechos() {
        System.out.println("Digite um trecho de episodio");
        scan.nextLine();
        var trechoEpisodio = scan.nextLine();
        List<Episodio> episodiosEncontrados = repository.buscaPorTrecho(trechoEpisodio);
        episodiosEncontrados.forEach(e -> {
            System.out.printf("""
                    Serie: %s, Temporada : %d, Episodio: %d, Titulo: %s
                    """, e.getSerie().getTitulo(), e.getNumerotemporada(),
                    e.getNumeroEpisodio(), e.getTitulo());
        });
    }


    private void top5MelhoresEpisodios() {
        buscarSeriePeloTitulo();
        if(serieEncontrada.isPresent()){
            Serie serie = serieEncontrada.get();
            List<Episodio> top5episodios = repository.buscarTop5MelhoresEpisodios(serie);
            top5episodios.forEach(e -> {
                System.out.printf("""
                    Serie: %s, Temporada : %d, Episodio: %d, Titulo: %s, Avaliação: %.2f
                    """, e.getSerie().getTitulo(), e.getNumerotemporada(),
                        e.getNumeroEpisodio(), e.getTitulo(), e.getAvaliacao());
            });
        }

    }

    private void buscarEpisodiosAposAnoDeLançamento(){
        buscarSeriePeloTitulo();
        if(serieEncontrada.isPresent()){
            Serie serie = serieEncontrada.get();
            System.out.println("Digite o ano de lançamento a ser filtrado");
            int anolancamento = scan.nextInt();
            List<Episodio> episodiosEncontrados = repository.buscarEpisodiosPeloAno(serie, anolancamento);

            episodiosEncontrados.forEach(System.out::println);
        }
    }



} // end class
