package com.chupe.chupeshop.repository;

import com.chupe.chupeshop.model.PedidoItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoItemRepository extends JpaRepository<PedidoItem, Long> {
    // Adicione métodos de consulta personalizados, se necessário
}