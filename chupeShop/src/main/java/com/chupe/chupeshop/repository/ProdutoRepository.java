package com.chupe.chupeshop.repository;

import com.chupe.chupeshop.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    @Query("SELECT p FROM Produto p WHERE p.preco BETWEEN :minPreco AND :maxPreco")
    List<Produto> findByPreco(@Param("minPreco") double minPreco, @Param("maxPreco") double maxPreco);
}