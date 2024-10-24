package com.chupe.chupeshop.controller;

import com.chupe.chupeshop.model.Carrinho;
import com.chupe.chupeshop.model.DTO.AdicionarProdutoDTO;
import com.chupe.chupeshop.model.DTO.CarrinhoRemoverDTO;
import com.chupe.chupeshop.model.DTO.ListarProdutosNoCarrinhoDTO;
import com.chupe.chupeshop.model.Produto;
import com.chupe.chupeshop.service.CarrinhoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carrinho")
public class CarrinhoController {
    private final CarrinhoService carrinhoService;

    public CarrinhoController(CarrinhoService carrinhoService) {
        this.carrinhoService = carrinhoService;
    }

    @PostMapping("/adicionar")
    public ResponseEntity<Carrinho> adicionarProdutoAoCarrinho(@RequestBody AdicionarProdutoDTO request) {
        Carrinho carrinho = carrinhoService.adicionarProdutoAoCarrinho(request.usuarioId(), request.produtoId(), request.quantidade());
        return ResponseEntity.ok(carrinho);
    }

    @DeleteMapping("/remover")
    public ResponseEntity<Carrinho> removerProdutoDoCarrinho(@RequestBody CarrinhoRemoverDTO request) {
        Carrinho carrinho = carrinhoService.removerProdutoDoCarrinho(request.usuarioId(), request.produtoId());
        return ResponseEntity.ok(carrinho);
    }
    @PostMapping("/listar")
    public ResponseEntity<List<Produto>> listarProdutosNoCarrinho(@RequestBody ListarProdutosNoCarrinhoDTO request) {
        List<Produto> produtos = carrinhoService.listarProdutosNoCarrinho(request.usuarioId());
        return ResponseEntity.ok(produtos);
    }
}