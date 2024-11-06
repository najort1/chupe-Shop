package com.chupechop.loja.repository;

import com.chupechop.loja.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByCpf(String cpf);


}
