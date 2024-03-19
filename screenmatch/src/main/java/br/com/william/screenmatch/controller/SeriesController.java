package br.com.william.screenmatch.controller;

import br.com.william.screenmatch.DTO.EpisodioDTO;
import br.com.william.screenmatch.DTO.SerieDTO;
import br.com.william.screenmatch.services.SeriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/series")
public class SeriesController {

    @Autowired
    private SeriesService seriesService;

    @GetMapping
    public List<SerieDTO> buscarSeries(){
        return seriesService.buscarTodasAsSeries();
    }

    @GetMapping("/top5")
    public List<SerieDTO> buscarTop5Series(){
        return  seriesService.buscarTop5Series();
    }

    @GetMapping("/lancamentos")
    public List<SerieDTO> buscarLancamentos(){
        return seriesService.buscarLancamentos();
    }

    @GetMapping("/{id}")
    public SerieDTO buscarPorId(@PathVariable Long id){
        return seriesService.buscarPorID(id);
    }

    @GetMapping("/{id}/temporadas/todas")
    public List<EpisodioDTO> buscarTodasTemporadas(@PathVariable Long id){
        return seriesService.buscarTodasTemporadas(id);
    };

    @GetMapping("/{id}/temporadas/{temporada}")
    public List<EpisodioDTO> buscarEpisodiosPorTemporada (@PathVariable Long id, @PathVariable Long temporada){
        return seriesService.buscarEpisodiosPorTemporada(id, temporada);
    }

    @GetMapping("/categoria/{categoria}")
    public List<SerieDTO> buscarSeriesPeloGenero(@PathVariable String categoria){
        return seriesService.buscarSeriesPeloGenero(categoria);
    }



}
