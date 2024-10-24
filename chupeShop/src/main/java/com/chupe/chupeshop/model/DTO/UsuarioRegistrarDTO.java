package com.chupe.chupeshop.model.DTO;

public record UsuarioRegistrarDTO(
        String nome,
        String email,
        String senha,
        String cpf,
        String telefone,
        String endereco
) {

}
