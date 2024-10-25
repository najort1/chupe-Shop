package com.chupe.chupeshop.service;

import com.chupe.chupeshop.model.Pedido;
import com.chupe.chupeshop.model.Usuario;
import com.chupe.chupeshop.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    public Pedido updateStatus(Long id, String status) {
        Optional<Pedido> pedidoOpt = pedidoRepository.findById(id);
        if (pedidoOpt.isPresent()) {
            Pedido pedido = pedidoOpt.get();
            pedido.setStatus(status);
            return pedidoRepository.save(pedido);
        }
        return null;
    }

    public List<Pedido> findByUsuario(Usuario usuario) {
        return pedidoRepository.findAll().stream()
                .filter(pedido -> pedido.getUsuario().equals(usuario))
                .collect(Collectors.toList());
    }

    public List<Pedido> findAll() {
        return pedidoRepository.findAll();
    }

    public Optional<Pedido> findById(Long id) {
        return pedidoRepository.findById(id);
    }

    public Pedido save(Pedido pedido) {
        calcularTotal(pedido);
        return pedidoRepository.save(pedido);
    }

    public void deleteById(Long id) {
        pedidoRepository.deleteById(id);
    }

    private void calcularTotal(Pedido pedido) {
        double total = pedido.getPedidoItems().stream()
                .mapToDouble(item -> item.getQuantidade() * item.getPreco())
                .sum();
        pedido.setTotal(total);
    }

}