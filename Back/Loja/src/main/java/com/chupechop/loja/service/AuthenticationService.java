package com.chupechop.loja.service;


import com.chupechop.loja.model.DTO.CadastroUsuarioDTO;
import com.chupechop.loja.model.DTO.LoginUsuarioDTO;
import com.chupechop.loja.model.Usuario;
import com.chupechop.loja.repository.UsuarioRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final UsuarioRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            UsuarioRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario cadastrar(CadastroUsuarioDTO input) {
        Usuario user = new Usuario();
        user.setNome(input.nome());
        user.setEmail(input.email());
        user.setSenha(passwordEncoder.encode(input.senha()));
        user.setCpf(input.cpf());

        return userRepository.save(user);
    }

    public Usuario logar(LoginUsuarioDTO input) {
        var authentication = new UsernamePasswordAuthenticationToken(input.email(), input.senha());
        var auth = authenticationManager.authenticate(authentication);
        return userRepository.findByEmail(input.email()).orElseThrow();
    }
}