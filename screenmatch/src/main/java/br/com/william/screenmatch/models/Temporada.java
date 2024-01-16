package br.com.william.screenmatch.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Temporada(@JsonAlias("Title") String titulo,
                        @JsonAlias("Season") Integer temporada,
                        @JsonAlias("Episodes") List<Episodio> episodios) {
}
