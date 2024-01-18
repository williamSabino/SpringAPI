package br.com.william.screenmatch.models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Episodio {

    DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private String titulo;
    private int numerotemporada;
    private int numeroEpisodio;
    private double avaliacao;
    private LocalDate dataLancamento;

    public Episodio(Integer temporada, DadosEpisodio dadosEpisodio) {
        this.numerotemporada = temporada;
        this.titulo = dadosEpisodio.titulo();
        this.numeroEpisodio = dadosEpisodio.numeroEpisodio();
        try {
            this.avaliacao = Double.parseDouble(dadosEpisodio.nota());
        } catch (NumberFormatException e){
            this.avaliacao = 0.0;
        }

        try{
            this.dataLancamento = LocalDate.parse(dadosEpisodio.dataLancamento());
        } catch (DateTimeParseException e){
            this.dataLancamento = null;
        }
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getNumerotemporada() {
        return numerotemporada;
    }

    public void setNumerotemporada(int numerotemporada) {
        this.numerotemporada = numerotemporada;
    }

    public int getNumeroEpisodio() {
        return numeroEpisodio;
    }

    public void setNumeroEpisodio(int numeroEpisodio) {
        this.numeroEpisodio = numeroEpisodio;
    }

    public double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public LocalDate getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(LocalDate dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    @Override
    public String toString() {
        return "Episodio{" +
                "titulo='" + titulo + '\'' +
                ", Numerotemporada=" + numerotemporada +
                ", numeroEpisodio=" + numeroEpisodio +
                ", Avaliacao=" + avaliacao +
                ", dataLancamento=" + dataLancamento.format(formatador) +
                '}';
    }
}
