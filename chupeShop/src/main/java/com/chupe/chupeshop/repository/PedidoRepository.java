package com.chupe.chupeshop.repository;

import com.chupe.chupeshop.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

}