package com.chupechop.loja.model.DTO;

public record RespostaEditarUsuarioDTO(
        String nome,
        String email,
        String cpf,
        String imagem
) {
}
