// PedidoItem.java
package com.chupe.chupeshop.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pedido_item")
public class PedidoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    @JsonBackReference // Evita serializar a referência de volta para o 'Pedido'
    private Pedido pedido;


    private Long produtoId;
    private String nome;
    private String descricao;
    private Float preco;
    private Integer quantidade;
    private Float total;
}