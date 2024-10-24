package com.chupe.chupeshop.model.DTO;

public record CriarProdutoDTO(
        String nome,
        String descricao,
        double preco,
        int estoque,
        String imagem,
        String categoria
) {
}
