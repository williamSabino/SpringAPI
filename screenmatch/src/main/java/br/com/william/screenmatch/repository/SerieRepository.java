package br.com.william.screenmatch.repository;

import br.com.william.screenmatch.models.Categoria;
import br.com.william.screenmatch.models.Episodio;
import br.com.william.screenmatch.models.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie, Long> {
    Optional<Serie> findByTituloContainingIgnoreCase(String titulo);

    List<Serie> findByAtoresContainingIgnoreCaseAndNotaGreaterThanEqual(String nomeAtor, Double avaliacao);

    List<Serie> findTop5ByOrderByNotaDesc();

    List<Serie> findByGenero(Categoria categoria);

    List<Serie> findByTotalDeTemporadasLessThanEqualAndNotaGreaterThanEqual(int temporadasLimite, double avaliacaoCorte);

    @Query("select s from Serie s WHERE s.totalDeTemporadas <= :totalTemporadas AND s.nota >= :avaliacao")
    List<Serie> buscaPorNumeroDeTemporadasEAvaliacao(int totalTemporadas, double avaliacao);

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE e.titulo ILIKE %:trechoEpisodio%")
    List<Episodio> buscaPorTrecho(String trechoEpisodio);
    @Query("SELECT e FROM Serie s JOIN s.episodios e where s = :serie ORDER BY e.avaliacao DESC LIMIT 5")
    List<Episodio> buscarTop5MelhoresEpisodios(Serie serie);
    @Query("SELECT e FROM Serie s JOIN s.episodios e where s = :serie AND YEAR(e.dataLancamento) >= :anolancamento")
    List<Episodio> buscarEpisodiosPeloAno(Serie serie, int anolancamento);

    List<Serie> findTop5ByOrderByEpisodiosDataLancamentoDesc();

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s.id = :id AND e.numerotemporada = :temporada")
    List<Episodio> buscarEpisodiosPelaTemporada(Long id, Long temporada);
}
