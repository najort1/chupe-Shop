package com.chupechop.loja.repository;

import com.chupechop.loja.model.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    Optional<Pedido> findByUsuario_Id(Long usuarioId);
    Optional<Pedido> findByUsuario_IdAndStatus(Long usuarioId, String status);
    Page<Pedido> findAllByUsuario_Id(Long usuarioId, Pageable pageable);
}