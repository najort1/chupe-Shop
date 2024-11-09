package com.chupechop.loja.model;

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
    private Usuario usuario;

    public void setUsuarioId(Long usuarioId) {
        this.usuario = new Usuario();
        this.usuario.setId(usuarioId);
    }

    public Long getUsuarioId() {
        return this.usuario.getId();
    }

    public void setProdutoId(Long produtoId) {
        Produto produto = new Produto();
        produto.setId(produtoId);
        this.produtos = List.of(produto);
    }

    public Long getProdutoId() {
        return this.produtos.get(0).getId();
    }

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
}