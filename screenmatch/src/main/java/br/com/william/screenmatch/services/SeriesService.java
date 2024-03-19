package br.com.william.screenmatch.services;

import br.com.william.screenmatch.DTO.EpisodioDTO;
import br.com.william.screenmatch.DTO.SerieDTO;
import br.com.william.screenmatch.models.Categoria;
import br.com.william.screenmatch.models.Episodio;
import br.com.william.screenmatch.models.Serie;
import br.com.william.screenmatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SeriesService {
    @Autowired
    SerieRepository serieRepository;


    public List<SerieDTO> buscarTodasAsSeries(){
        return converterRequisicoes(serieRepository.findAll());
    }

    public List<SerieDTO> buscarTop5Series() {
        return converterRequisicoes(serieRepository.findTop5ByOrderByNotaDesc());
    }

    public List<SerieDTO> buscarLancamentos(){
        return converterRequisicoes(serieRepository.findTop5ByOrderByEpisodiosDataLancamentoDesc());
    }


    public SerieDTO buscarPorID(Long id) {
        Optional<Serie> serie = serieRepository.findById(id);

        if (serie.isPresent()){
            Serie s = serie.get();
            return new SerieDTO(s.getId(),
                    s.getTitulo(),
                    s.getTotalDeTemporadas(),
                    s.getNota(),
                    s.getGenero(),
                    s.getAtores(),
                    s.getPoster(),
                    s.getSinopse());
        }
        return null;
    }

    public List<EpisodioDTO> buscarTodasTemporadas(Long id) {
        Optional<Serie> serie = serieRepository.findById(id);

        if (serie.isPresent()){
            Serie s = serie.get();
            return s.getEpisodios().stream()
                    .map(e -> new EpisodioDTO(
                            e.getNumerotemporada(),
                            e.getNumeroEpisodio(),
                            e.getTitulo()))
                    .toList();
        }
        return null;
    }

    public List<EpisodioDTO> buscarEpisodiosPorTemporada(Long id, Long temporada) {
        Optional<Serie> serie = serieRepository.findById(id);

        if (serie.isPresent()){
            List<Episodio> episodios = serieRepository.buscarEpisodiosPelaTemporada(serie.get().getId(), temporada);
            return episodios.stream()
                    .map(e -> new EpisodioDTO(
                            e.getNumerotemporada(),
                            e.getNumeroEpisodio(),
                            e.getTitulo()
                    )).toList();
        }
        return null;
    }

    public List<SerieDTO> buscarSeriesPeloGenero(String categoria) {
        Categoria categoriaEnum = Categoria.selecionarCategoriaPortugues(categoria);
        return converterRequisicoes(serieRepository.findByGenero(categoriaEnum));
    }

    private List<SerieDTO> converterRequisicoes(List<Serie> series){
        return series.stream()
                .map(s -> new SerieDTO(s.getId(),
                        s.getTitulo(),
                        s.getTotalDeTemporadas(),
                        s.getNota(),
                        s.getGenero(),
                        s.getAtores(),
                        s.getPoster(),
                        s.getSinopse())).toList();
    }
}
