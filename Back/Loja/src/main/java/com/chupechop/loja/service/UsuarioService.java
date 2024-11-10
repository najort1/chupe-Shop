package com.chupechop.loja.service;


import com.chupechop.loja.model.DTO.EditarUsuarioDTO;
import com.chupechop.loja.model.Usuario;
import com.chupechop.loja.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public void deleteById(Long id) {
        usuarioRepository.deleteById(id);
    }

    public Usuario editarUsuario(Long id, EditarUsuarioDTO editarUsuarioDTO) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        usuario.setNome(editarUsuarioDTO.nome());
        usuario.setCpf(editarUsuarioDTO.cpf());
        usuario.setImagem(editarUsuarioDTO.imagem());
        return usuarioRepository.save(usuario);
    }

}
