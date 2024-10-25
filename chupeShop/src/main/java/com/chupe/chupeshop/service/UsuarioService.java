package com.chupe.chupeshop.service;

import com.chupe.chupeshop.model.Usuario;
import com.chupe.chupeshop.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }


}
