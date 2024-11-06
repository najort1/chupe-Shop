package com.chupechop.loja.service;

import com.chupechop.loja.model.Produto;
import com.chupechop.loja.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public void deleteById(Long id) {
        produtoRepository.deleteById(id);
    }



    public Page<Produto> findAll(Pageable pageable) {
        return produtoRepository.findAll(pageable);
    }

    public Produto findById(Long id) {
        return produtoRepository.findById(id).orElse(null);
    }

    public Produto save(Produto produto) {
        return produtoRepository.save(produto);
    }

    public Produto update(Produto produto) {
        return produtoRepository.save(produto);
    }

    public List<Produto> findByCategoria(String categoria) {
        return produtoRepository.findByCategoria(categoria);
    }

    public List<Produto> findByNomeContaining(String nome) {
        return produtoRepository.findByNomeContaining(nome);
    }

}
