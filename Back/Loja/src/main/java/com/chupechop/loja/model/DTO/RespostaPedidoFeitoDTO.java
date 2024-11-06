package com.chupechop.loja.model.DTO;

public record RespostaPedidoFeitoDTO(
        Long id,
        Long usuarioId,
        Long produtoId,
        Integer quantidade,
        Double total,
        String imagem,
        String nome,
        Double preco
) {
}
