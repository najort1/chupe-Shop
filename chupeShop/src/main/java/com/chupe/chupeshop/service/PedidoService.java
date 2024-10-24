package com.chupe.chupeshop.service;

import com.chupe.chupeshop.model.Carrinho;
import com.chupe.chupeshop.model.ItemCarrinho;
import com.chupe.chupeshop.model.Pedido;
import com.chupe.chupeshop.repository.CarrinhoRepository;
import com.chupe.chupeshop.repository.ItemCarrinhoRepository;
import com.chupe.chupeshop.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final CarrinhoRepository carrinhoRepository;
    private final ItemCarrinhoRepository itemCarrinhoRepository;

    public Pedido finalizarCompra(Long usuarioId) {
        Carrinho carrinho = carrinhoRepository.findByUsuarioId(usuarioId);
        if (carrinho == null || carrinho.getItens().isEmpty()) {
            throw new RuntimeException("Carrinho vazio ou não encontrado");
        }

        Pedido pedido = new Pedido();
        pedido.setUsuario(carrinho.getUsuario());
        pedido.setCarrinho(carrinho);

        // Preencher os campos do pedido com as informações do usuário e do carrinho
        pedido.setNome(carrinho.getUsuario().getNome());
        pedido.setEmail(carrinho.getUsuario().getEmail());
        pedido.setCpf(carrinho.getUsuario().getCpf());
        pedido.setTelefone(carrinho.getUsuario().getTelefone());
        pedido.setEndereco(carrinho.getUsuario().getEndereco());
        pedido.setStatus("Pendente");
        pedido.setData(LocalDateTime.now().toString());
        pedido.setFormaPagamento("Cartão de Crédito"); // Exemplo, pode ser ajustado conforme necessário

        // Preencher os campos produto e quantidade com as informações dos itens do carrinho
        String produtos = carrinho.getItens().stream()
                .map(item -> item.getProduto().getNome())
                .collect(Collectors.joining(", "));
        String quantidades = carrinho.getItens().stream()
                .map(item -> String.valueOf(item.getQuantidade()))
                .collect(Collectors.joining(", "));

        pedido.setProduto(produtos);
        pedido.setQuantidade(quantidades);

        // Salvar o pedido antes de associar os itens
        pedido = pedidoRepository.save(pedido);

        Set<ItemCarrinho> itens = new HashSet<>();
        for (ItemCarrinho item : carrinho.getItens()) {
            item.setPedido(pedido); // Associar o item ao pedido
            itens.add(item);
        }
        pedido.setItens(itens);
        pedido.setTotal(itens.stream().mapToDouble(item -> item.getProduto().getPreco() * item.getQuantidade()).sum());

        // Remover os itens do carrinho e do banco de dados
        itemCarrinhoRepository.deleteAll(carrinho.getItens());
        carrinho.getItens().clear();
        carrinhoRepository.save(carrinho);

        // Salvar os itens do pedido
        for (ItemCarrinho item : itens) {
            itemCarrinhoRepository.save(item);
        }

        return pedido;
    }
}