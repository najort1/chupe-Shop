package com.chupechop.loja.model.DTO;

public record DadosUsuarioDTO(
        String nome,
        String email,
        String cpf,
        String imagem
) {
}
