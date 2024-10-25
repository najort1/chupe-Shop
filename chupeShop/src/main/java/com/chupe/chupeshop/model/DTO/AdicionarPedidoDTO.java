package com.chupe.chupeshop.model.DTO;

public record AdicionarPedidoDTO(
        Long usuarioId,
        Long produtoId,
        int quantidade
) {
}
