package com.chupechop.loja.model.DTO;

public record CadastroUsuarioDTO(
        String nome,
        String email,
        String senha,
        String cpf
) {
}
