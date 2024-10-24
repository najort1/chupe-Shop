package com.chupe.chupeshop.repository;

import com.chupe.chupeshop.model.ItemCarrinho;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemCarrinhoRepository extends JpaRepository<ItemCarrinho, Long> {

        ItemCarrinho findByCarrinhoId(Long carrinhoId);

}
