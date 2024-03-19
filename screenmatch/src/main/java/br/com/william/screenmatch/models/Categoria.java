package br.com.william.screenmatch.models;

public enum Categoria {
    ACAO("Action", "ação"),
    COMEDIA("Comedy", "comedia"),
    DRAMA("Drama", "drama"),
    CRIME("Crime", "crime"),
    POLICIAL("Police", "policial"),
    AVENTURA("Adventure", "aventura"),
    ROMANCE("Romance", "romance");

    private String categoria;
    private String categoriaPortugues;

    Categoria(String categoriaOMDB, String categoriaPortugues) {
        this.categoriaPortugues = categoriaPortugues;
        this.categoria = categoriaOMDB;
    }

    public static Categoria selecionarCategoria(String texto){
        for (Categoria categoria : Categoria.values()){
            if (categoria.categoria.equalsIgnoreCase(texto)){
                return categoria;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada : " + texto);
    }

    public static Categoria selecionarCategoriaPortugues(String texto){
        for (Categoria categoria : Categoria.values()){
            if(categoria.categoriaPortugues.equalsIgnoreCase(texto)){
                return categoria;
            }
        }
        throw new IllegalArgumentException(("Nenhuma categoria encontrada : " + texto));
    }

}
