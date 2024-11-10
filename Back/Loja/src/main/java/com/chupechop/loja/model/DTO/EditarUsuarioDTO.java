package com.chupechop.loja.model.DTO;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)

public record EditarUsuarioDTO(
    @JsonProperty("nome") String nome,
    @JsonProperty("email") String email,
    @JsonProperty("cpf") String cpf,
    @JsonProperty("imagem") String imagem

) {
}
