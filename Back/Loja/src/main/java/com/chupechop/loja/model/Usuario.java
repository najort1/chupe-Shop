package com.chupechop.loja.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@RequiredArgsConstructor
@Table(name = "usuario")
@Data
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "senha")
    private String senha;

    @Column(name = "cpf", unique = true)
    private String cpf;

    @Column(name = "imagem")
    private String imagem;

    @Column(name = "criado_em")
    private Date criadoEm;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of((GrantedAuthority) () -> "ROLE_USER");
    }

    @Override
    public String getUsername() {
        return email;
    }
    @Override
    public String getPassword() {
        return senha;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @PrePersist
    public void prePersist() {
        criadoEm = new Date();
    }

}
