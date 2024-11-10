package com.chupechop.loja.controller;

import com.chupechop.loja.model.DTO.FazerPedidoDTO;
import com.chupechop.loja.model.DTO.RespostaPedidoFeitoDTO;
import com.chupechop.loja.model.Pedido;
import com.chupechop.loja.model.Produto;
import com.chupechop.loja.model.Usuario;
import com.chupechop.loja.service.PedidoService;
import com.chupechop.loja.service.ProdutoService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedido")
public class PedidoController {

    private final PedidoService pedidoService;
    private final ProdutoService produtoService;

    public PedidoController(PedidoService pedidoService, ProdutoService produtoService) {
        this.pedidoService = pedidoService;
        this.produtoService = produtoService;

    }

    @GetMapping("/meus-pedidos")
    public ResponseEntity<Page<Pedido>> listarPedidosDoUsuarioLogado(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuarioLogado = (Usuario) authentication.getPrincipal();
        Page<Pedido> pedidos = pedidoService.findAllByUsuarioId(usuarioLogado.getId(), PageRequest.of(page, size));
        return ResponseEntity.ok(pedidos);
    }

    @PostMapping("/fazer-pedido")
    public ResponseEntity<RespostaPedidoFeitoDTO> fazerPedido(@RequestBody FazerPedidoDTO fazerPedidoDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuarioLogado = (Usuario) authentication.getPrincipal();

        // Verifique se o produto existe antes de prosseguir
        Produto produto = produtoService.findById(fazerPedidoDTO.produtoId());
        if (produto == null) {
            return ResponseEntity.status(404).build(); // Produto não encontrado
        }

        Pedido pedido = pedidoService.save(fazerPedidoDTO, usuarioLogado);

        produtoService.diminuirEstoque(pedido.getProdutoId(), pedido.getQuantidade());

        RespostaPedidoFeitoDTO resposta = new RespostaPedidoFeitoDTO(
                pedido.getId(),
                pedido.getUsuarioId(),
                pedido.getProdutoId(),
                pedido.getQuantidade(),
                pedido.getPreco(),
                produto.getImagem(),
                produto.getNome(),
                produto.getPreco()
        );
        return ResponseEntity.ok(resposta);
    }

    @Transactional
    @DeleteMapping("/deletar-pedido/{id}")
    public ResponseEntity<Void> deletarPedido(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuarioLogado = (Usuario) authentication.getPrincipal();
        Pedido pedido = pedidoService.findById(id);
        if (pedido == null) {
            return ResponseEntity.status(404).build(); // Pedido não encontrado
        }
        if (pedido.getUsuarioId().equals(usuarioLogado.getId())) {
            produtoService.aumentarEstoque(pedido.getProdutoId(), pedido.getQuantidade());
            pedidoService.deleteById(id);
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(403).build(); // Usuário não autorizado
    }

    @GetMapping("/finalizar-pedido/{id}")
    public ResponseEntity<Void> finalizarPedido(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuarioLogado = (Usuario) authentication.getPrincipal();
        Pedido pedido = pedidoService.findById(id);
        if (pedido == null) {
            return ResponseEntity.status(404).build(); // Pedido não encontrado
        }

        System.out.println("Pedido.getUsuarioId(): " + pedido.getUsuarioId());
        System.out.println("UsuarioLogado.getId(): " + usuarioLogado.getId());
        if (pedido.getUsuarioId().equals(usuarioLogado.getId())) {
            System.out.println("Pedido finalizado");
            pedidoService.finalizarPedido(id);
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(403).build(); // Usuário não autorizado
    }

    @GetMapping("/pedidos-finalizados")
    public ResponseEntity<List<Pedido>> listarPedidosFinalizados() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuarioLogado = (Usuario) authentication.getPrincipal();
        List<Pedido> pedidosFinalizados = pedidoService.acharTodosPedidosFinalizados(usuarioLogado.getId());
        return ResponseEntity.ok(pedidosFinalizados);
    }


}
