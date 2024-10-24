package com.chupe.chupeshop.controller;

import com.chupe.chupeshop.model.Pedido;
import com.chupe.chupeshop.service.PedidoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedido")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping("/finalizar")
    public ResponseEntity<Pedido> finalizarPedido(@RequestParam Long usuarioId) {
        Pedido pedido = pedidoService.finalizarCompra(usuarioId);
        return ResponseEntity.ok(pedido);
    }
}