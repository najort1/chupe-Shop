package com.chupe.chupeshop.model.DTO;

public record AdicionarProdutoDTO(
        Long usuarioId,
        Long produtoId,
        int quantidade
) {
}
