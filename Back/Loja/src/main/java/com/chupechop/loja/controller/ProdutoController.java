package com.chupechop.loja.controller;

import com.chupechop.loja.model.Produto;
import com.chupechop.loja.service.ProdutoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping("/listar")
    public ResponseEntity<Page<Produto>> listarProdutos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<Produto> produtos = produtoService.findAll(PageRequest.of(page, size));
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/listar/nome/")
    public ResponseEntity<List<Produto>> listarProdutosPorNome(String nome) {
        return ResponseEntity.ok(produtoService.findByNomeContaining(nome));
    }

}