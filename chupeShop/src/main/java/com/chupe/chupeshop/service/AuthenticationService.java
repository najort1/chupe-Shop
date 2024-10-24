package com.chupe.chupeshop.service;


import com.chupe.chupeshop.model.DTO.UsuarioLoginDTO;
import com.chupe.chupeshop.model.DTO.UsuarioRegistrarDTO;
import com.chupe.chupeshop.model.Usuario;
import com.chupe.chupeshop.repository.UsuarioRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UsuarioRepository usuarioRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(UsuarioRepository usuarioRepository, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario registrarUsuario(UsuarioRegistrarDTO usuarioRegistrarDTO) {
        if(usuarioRepository.findByEmail(usuarioRegistrarDTO.email()).isPresent()){
            throw new RuntimeException("Email já cadastrado");
        }

        Usuario usuario = new Usuario();
        usuario.setEmail(usuarioRegistrarDTO.email());
        usuario.setSenha(passwordEncoder.encode(usuarioRegistrarDTO.senha()));
        usuario.setNome(usuarioRegistrarDTO.nome());
        usuario.setCpf(usuarioRegistrarDTO.cpf());
        usuario.setTelefone(usuarioRegistrarDTO.telefone());
        usuario.setEndereco(usuarioRegistrarDTO.endereco());
        usuario.setAtivo(true);
        usuario.setAdmin(false);
        return usuarioRepository.save(usuario);
    }

    public Usuario loginUsuario(UsuarioLoginDTO usuarioLoginDTO) {

        Usuario usuario = usuarioRepository.findByEmail(usuarioLoginDTO.email())
                .orElseThrow(() -> new RuntimeException("Email ou senha inválidos"));

        if(!passwordEncoder.matches(usuarioLoginDTO.senha(), usuario.getSenha())){
            throw new RuntimeException("Email ou senha inválidos");
        }

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usuarioLoginDTO.email(), usuarioLoginDTO.senha()));

        return usuario;

    }

}