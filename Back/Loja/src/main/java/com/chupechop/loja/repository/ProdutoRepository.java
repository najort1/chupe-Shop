package com.chupechop.loja.repository;

import com.chupechop.loja.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    List<Produto> findByNomeContaining(String nome);
    List<Produto> findByCategoria(String categoria);


}
