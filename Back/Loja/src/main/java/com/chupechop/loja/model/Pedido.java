package com.chupechop.loja.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "pedido")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quantidade")
    private Integer quantidade;

    @Column(name = "preco")
    private Double preco;

    @Column(name = "status")
    private String status;

    @Column(name = "data")
    private Date data;

    @Column(name = "endereco")
    private String endereco;

    @ManyToMany
    @JoinTable(
            name = "pedido_produto",
            joinColumns = @JoinColumn(name = "pedido_id"),
            inverseJoinColumns = @JoinColumn(name = "produto_id")
    )
    private List<Produto> produtos;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @JsonBackReference
    private Usuario usuario;

    @PrePersist
    public void prePersist() {
        this.data = new Date();
        this.status = "PENDENTE";
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", quantidade=" + quantidade +
                ", preco=" + preco +
                ", status='" + status + '\'' +
                ", data=" + data +
                ", endereco='" + endereco + '\'' +
                ", produtos=" + produtos +
                '}';
    }

    public Long getProdutoId() {
        return produtos != null && !produtos.isEmpty() ? produtos.get(0).getId() : null;
    }

    public void setProdutoId(Long produtoId) {
        if (produtos != null && !produtos.isEmpty()) {
            produtos.get(0).setId(produtoId);
        } else {
            Produto produto = new Produto();
            produto.setId(produtoId);
            produtos = List.of(produto);
        }
    }

    public Long getUsuarioId() {
        return usuario != null ? usuario.getId() : null;
    }

    public void setUsuarioId(Long usuarioId) {
        if (usuario == null) {
            usuario = new Usuario();
        }
        usuario.setId(usuarioId);
    }
}