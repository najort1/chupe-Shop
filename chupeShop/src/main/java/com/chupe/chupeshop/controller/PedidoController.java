package com.chupe.chupeshop.controller;

import com.chupe.chupeshop.model.DTO.AdicionarPedidoDTO;
import com.chupe.chupeshop.model.Pedido;
import com.chupe.chupeshop.model.PedidoItem;
import com.chupe.chupeshop.model.Produto;
import com.chupe.chupeshop.model.Usuario;
import com.chupe.chupeshop.service.PedidoService;
import com.chupe.chupeshop.service.ProdutoService;
import com.chupe.chupeshop.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedido")
public class PedidoController {

    private final PedidoService pedidoService;
    private final UsuarioService usuarioService;
    private final ProdutoService produtoService;

    public PedidoController(PedidoService pedidoService, UsuarioService usuarioService, ProdutoService produtoService) {
        this.pedidoService = pedidoService;
        this.usuarioService = usuarioService;
        this.produtoService = produtoService;
    }

    @GetMapping("/listar")
    public ResponseEntity<?> listarPedidos() {
        return ResponseEntity.ok(pedidoService.findAll());
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> buscarPedido(@PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.findById(id));
    }

    @PostMapping("/adicionar")
    public ResponseEntity<?> adicionarPedido(@RequestBody AdicionarPedidoDTO adicionarPedidoDTO) {
        Usuario usuario = usuarioService.findById(adicionarPedidoDTO.usuarioId()).get();
        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setNome(usuario.getNome());
        pedido.setEndereco(usuario.getEndereco());
        pedido.setTelefone(usuario.getTelefone());

        Produto produto = produtoService.findById(adicionarPedidoDTO.produtoId()).get();
        PedidoItem pedidoItem = new PedidoItem();
        pedidoItem.setProdutoId(produto.getId());
        pedidoItem.setNome(produto.getNome());
        pedidoItem.setDescricao(produto.getDescricao());
        pedidoItem.setPreco((float) produto.getPreco());
        pedidoItem.setQuantidade(adicionarPedidoDTO.quantidade());
        pedidoItem.setTotal((float) (produto.getPreco() * adicionarPedidoDTO.quantidade()));
        pedidoItem.setPedido(pedido);

        pedido.getPedidoItems().add(pedidoItem);
        pedido.setTotal(produto.getPreco() * adicionarPedidoDTO.quantidade());
        pedido.setStatus("PENDENTE");
        pedido.setFormaPagamento("DINHEIRO");

        return ResponseEntity.ok(pedidoService.save(pedido));
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizarPedido(@PathVariable Long id, @RequestBody Pedido pedido) {
        Pedido pedidoAtualizado = pedidoService.updateStatus(id, pedido.getStatus());
        return ResponseEntity.ok(pedidoAtualizado);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deletarPedido(@PathVariable Long id) {
        pedidoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }




}