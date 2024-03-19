package br.com.william.screenmatch.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosSeries(@JsonAlias("Title") String titulo,
                          @JsonAlias("totalSeasons") Integer totalDeTemporadas,
                          @JsonAlias("imdbRating") String nota,
                          @JsonAlias("Genre") String genero,
                          @JsonAlias("Actors")String atores,
                          @JsonAlias("Poster") String poster,
                          @JsonAlias("Plot") String sinopse){
}
