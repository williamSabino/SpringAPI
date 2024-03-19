package br.com.william.screenmatch.DTO;

import br.com.william.screenmatch.models.Categoria;

public record SerieDTO(
         Long id,
         String titulo,
         Integer totalDeTemporadas,
         double nota,
         Categoria genero,
         String atores,
         String poster,
         String sinopse
) {
}
