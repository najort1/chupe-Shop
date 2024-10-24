package com.chupe.chupeshop.repository;

import com.chupe.chupeshop.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByEmail(String email);

    Usuario findByEmailAndSenha(String email, String senha);
}
