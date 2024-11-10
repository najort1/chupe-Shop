package com.chupechop.loja.service;

import com.chupechop.loja.model.DTO.FazerPedidoDTO;
import com.chupechop.loja.model.Pedido;
import com.chupechop.loja.model.Produto;
import com.chupechop.loja.model.Usuario;
import com.chupechop.loja.repository.PedidoRepository;
import com.chupechop.loja.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;

    public void deleteById(Long id) {
        pedidoRepository.deleteById(id);
    }

    public Page<Pedido> findAllByUsuarioId(Long id, Pageable pageable) {
        return pedidoRepository.findAllByUsuario_Id(id, pageable);
    }

    public Pedido findById(Long id) {
        return pedidoRepository.findById(id).orElse(null);
    }

    public Pedido save(FazerPedidoDTO fazerPedidoDTO, Usuario usuario) {
        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setProdutoId(fazerPedidoDTO.produtoId());
        pedido.setQuantidade(fazerPedidoDTO.quantidade());
        pedido.setPreco(calcularPreco(fazerPedidoDTO));
        return pedidoRepository.save(pedido);
    }

    private Double calcularPreco(FazerPedidoDTO fazerPedidoDTO) {
        Produto produto = produtoRepository.findById(fazerPedidoDTO.produtoId())
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));
        return produto.getPreco() * fazerPedidoDTO.quantidade();
    }

    public Pedido update(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }


    public void finalizarPedido(Long id) {
        Pedido pedido = pedidoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado"));
        pedido.setStatus("FINALIZADO");
        pedidoRepository.save(pedido);
    }

    public List<Pedido> acharTodosPedidosFinalizados(Long usuarioId) {
        return pedidoRepository.findAllByUsuario_IdAndStatusEquals(usuarioId, "FINALIZADO");
    }
}
