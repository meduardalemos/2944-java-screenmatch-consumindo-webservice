package br.com.alura.screenmatch.modelos;

/* Cria record apenas para armazenar os dados obtidos via API e poder converter no objeto de tipo
meuTitulo */
public record TituloOmdb(String title, String year, String runtime) {
}
