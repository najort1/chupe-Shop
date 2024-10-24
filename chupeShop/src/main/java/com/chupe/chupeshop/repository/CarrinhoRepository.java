package com.chupe.chupeshop.repository;

import com.chupe.chupeshop.model.Carrinho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface CarrinhoRepository extends JpaRepository<Carrinho, Long > {

    Carrinho findByUsuarioId(Long usuarioId);

}
