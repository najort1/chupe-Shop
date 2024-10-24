package com.chupe.chupeshop.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "usuario")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotEmpty(message = "O campo nome é obrigatório")
    @NotNull(message = "O campo nome é obrigatório")
    private String nome;

    @Column(unique = true)
    @NotEmpty(message = "O campo email é obrigatório")
    @NotNull(message = "O campo email é obrigatório")
    private String email;

    @Column
    @NotEmpty(message = "O campo senha é obrigatório")
    @NotNull(message = "O campo senha é obrigatório")
    private String senha;

    @Column(unique = true)
    @NotEmpty(message = "O campo cpf é obrigatório")
    @NotNull(message = "O campo cpf é obrigatório")
    private String cpf;

    @Column
    @NotEmpty(message = "O campo telefone é obrigatório")
    @NotNull(message = "O campo telefone é obrigatório")
    private String telefone;

    @Column
    @NotEmpty(message = "O campo endereco é obrigatório")
    @NotNull(message = "O campo endereco é obrigatório")
    private String endereco;

    @Column
    private Boolean ativo;
    @Column
    private Boolean admin;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Pedido> pedidos;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Carrinho carrinho;



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.ativo;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.ativo;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.ativo;
    }

    @Override
    public boolean isEnabled() {
        return this.ativo;
    }

}