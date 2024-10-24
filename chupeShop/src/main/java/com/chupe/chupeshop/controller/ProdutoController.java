package com.chupe.chupeshop.controller;

import com.chupe.chupeshop.model.Produto;
import com.chupe.chupeshop.service.ProdutoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Produto>> listarProdutos() {
        List<Produto> produtos = produtoService.listarProdutos();
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/buscar/preco/{minPreco}/{maxPreco}")
    public ResponseEntity<List<Produto>> buscarProdutosPorPreco(double minPreco, double maxPreco) {
        List<Produto> produtos = produtoService.findByPreco(minPreco, maxPreco);
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/detalhes/{id}")
    public ResponseEntity<Produto> detalhesProduto(@PathVariable Long id) {
        Produto produto = produtoService.detalhesProduto(id);
        return ResponseEntity.ok(produto);
    }

    @PostMapping("/adicionar")
    public ResponseEntity<Produto> adicionarProduto(Produto produto) {
        Produto produtoAdicionado = produtoService.adicionarProduto(produto);
        return ResponseEntity.ok(produtoAdicionado);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletarProduto(@PathVariable Long id) {
        produtoService.deletarProduto(id);
        return ResponseEntity.noContent().build();
    }



}
