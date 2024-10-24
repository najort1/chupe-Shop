package com.chupe.chupeshop.service;

import com.chupe.chupeshop.model.Carrinho;
import com.chupe.chupeshop.model.ItemCarrinho;
import com.chupe.chupeshop.model.Produto;
import com.chupe.chupeshop.repository.CarrinhoRepository;
import com.chupe.chupeshop.repository.ProdutoRepository;
import com.chupe.chupeshop.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarrinhoService {

    private final CarrinhoRepository carrinhoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProdutoRepository produtoRepository;

    public Carrinho adicionarProdutoAoCarrinho(Long usuarioId, Long produtoId, int quantidade) {
        Carrinho carrinho = carrinhoRepository.findByUsuarioId(usuarioId);
        if (carrinho == null) {
            carrinho = new Carrinho();
            carrinho.setUsuario(usuarioRepository.findById(usuarioId).orElseThrow(() -> new RuntimeException("Usuário não encontrado")));
        }

        Produto produto = produtoRepository.findById(produtoId).orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        Optional<ItemCarrinho> existingItem = carrinho.getItens().stream()
                .filter(item -> item.getProduto().getId().equals(produtoId))
                .findFirst();

        if (existingItem.isPresent()) {
            ItemCarrinho itemCarrinho = existingItem.get();
            itemCarrinho.setQuantidade(itemCarrinho.getQuantidade() + quantidade);
            itemCarrinho.setPreco(formatPrice(produto.getPreco() * itemCarrinho.getQuantidade()));
        } else {
            ItemCarrinho itemCarrinho = new ItemCarrinho();
            itemCarrinho.setProduto(produto);
            itemCarrinho.setQuantidade(quantidade);
            itemCarrinho.setPreco(formatPrice(produto.getPreco() * quantidade));
            itemCarrinho.setCarrinho(carrinho);
            carrinho.getItens().add(itemCarrinho);
        }

        return carrinhoRepository.save(carrinho);
    }

    private double formatPrice(double price) {
        BigDecimal bd = new BigDecimal(price).setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public Carrinho removerProdutoDoCarrinho(Long usuarioId, Long produtoId) {
        Carrinho carrinho = carrinhoRepository.findByUsuarioId(usuarioId);
        if (carrinho == null) {
            throw new RuntimeException("Carrinho não encontrado");
        }

        carrinho.getItens().removeIf(item -> item.getProduto().getId().equals(produtoId));
        return carrinhoRepository.save(carrinho);
    }

    public List<Produto> listarProdutosNoCarrinho(Long usuarioId) {
        Carrinho carrinho = carrinhoRepository.findByUsuarioId(usuarioId);
        if (carrinho == null) {
            throw new RuntimeException("Carrinho não encontrado");
        }

        return carrinho.getItens().stream().map(ItemCarrinho::getProduto).toList();
    }
}