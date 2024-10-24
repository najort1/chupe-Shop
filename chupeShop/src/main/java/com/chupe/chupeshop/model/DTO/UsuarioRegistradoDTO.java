package com.chupe.chupeshop.model.DTO;

public record UsuarioRegistradoDTO(
        String nome,
        String email,
        String cpf,
        String telefone,
        String endereco
) {
}