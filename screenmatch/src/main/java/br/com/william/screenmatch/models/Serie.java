package br.com.william.screenmatch.models;

import br.com.william.screenmatch.services.TraduzirOpenAI;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

@Entity
@Table(name = "series")
public class Serie {
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String titulo;
    private Integer totalDeTemporadas;
    private double nota;
    @Enumerated(EnumType.STRING)
    private Categoria genero;
    private String atores;
    private String poster;
    private String sinopse;

    @OneToMany(mappedBy = "serie", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Episodio> episodios = new ArrayList<>();

    public Serie (){}

    public Serie(DadosSeries dadosSeries) {
        this.titulo = dadosSeries.titulo();
        this.totalDeTemporadas = dadosSeries.totalDeTemporadas();
        this.nota = OptionalDouble.of(Double.valueOf(dadosSeries.nota())).orElse(0);
        this.genero = Categoria.selecionarCategoria(dadosSeries.genero().split(",")[0]);
        this.atores = dadosSeries.atores();
        this.poster = dadosSeries.poster();
        this.sinopse = TraduzirOpenAI.traduzirTexto(dadosSeries.sinopse());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Episodio> getEpisodios() {
        return episodios;
    }

    public void setEpisodios(List<Episodio> episodios) {
        episodios.forEach(e -> e.setSerie(this));
        this.episodios = episodios;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getTotalDeTemporadas() {
        return totalDeTemporadas;
    }

    public void setTotalDeTemporadas(Integer totalDeTemporadas) {
        this.totalDeTemporadas = totalDeTemporadas;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public Categoria getGenero() {
        return genero;
    }

    public void setGenero(Categoria genero) {
        this.genero = genero;
    }

    public String getAtores() {
        return atores;
    }

    public void setAtores(String atores) {
        this.atores = atores;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    @Override
    public String toString() {
        return  "genero=" + genero +
                ", titulo='" + titulo + '\'' +
                ", totalDeTemporadas=" + totalDeTemporadas +
                ", nota=" + nota +
                ", atores='" + atores + '\'' +
                ", poster='" + poster +
                ", sinopse='" + sinopse +
                ", episodios =" + episodios + '\'' +
                '}';
    }
}
