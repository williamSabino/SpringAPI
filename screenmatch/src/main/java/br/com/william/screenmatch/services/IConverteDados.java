package br.com.william.screenmatch.services;

public interface IConverteDados {
    <T> T converterDados(String json, Class<T> classe);
}
