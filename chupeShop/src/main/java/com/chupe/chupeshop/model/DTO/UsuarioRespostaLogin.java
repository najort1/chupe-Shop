package com.chupe.chupeshop.model.DTO;

public record UsuarioRespostaLogin(
        String nome,
        String email,
        String cpf,
        String telefone,
        String endereco,
        String token
) {
}