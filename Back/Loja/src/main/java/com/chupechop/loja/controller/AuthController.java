package com.chupechop.loja.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Optional;

import com.chupechop.loja.model.DTO.*;
import com.chupechop.loja.model.Usuario;
import com.chupechop.loja.repository.UsuarioRepository;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chupechop.loja.service.AuthenticationService;
import com.chupechop.loja.service.GoogleAuthService;
import com.chupechop.loja.service.JwtService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtService jwtService;
    private final AuthenticationService authenticationService;
    private final GoogleAuthService googleAuthService;
    private final UsuarioRepository usuarioRepository;

    public AuthController(JwtService jwtService, AuthenticationService authenticationService , GoogleAuthService googleAuthService, UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
        this.googleAuthService = googleAuthService;
    }


    @PostMapping("/login")
    public ResponseEntity<RespostaLoginDTO> login(@RequestBody LoginUsuarioDTO loginUsuarioDTO){
        var usuario = authenticationService.logar(loginUsuarioDTO);
        var token = jwtService.generateToken(usuario);
        return ResponseEntity.ok(new RespostaLoginDTO(token, usuario.getNome()));
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<RespostaLoginDTO> cadastrar(@RequestBody CadastroUsuarioDTO cadastroUsuarioDTO){
        var usuario = authenticationService.cadastrar(cadastroUsuarioDTO);
        var token = jwtService.generateToken(usuario);
        return ResponseEntity.ok(new RespostaLoginDTO(token, usuario.getNome()));
    }


    @PostMapping("/google-login")
    public ResponseEntity<String> googleLogin(@RequestBody ConverterTokenDTO converterTokenDTO) {
        try {
            // Verifica o token do Google
            GoogleIdToken.Payload payload = googleAuthService.getPayload(converterTokenDTO.token());
            String email = payload.getEmail();

            // Verifica se o usuário já existe no banco de dados
            Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(email);
            Usuario usuario;
            if (usuarioOptional.isEmpty()) {
                // Se o usuário não existir, cadastra automaticamente
                usuario = new Usuario();
                usuario.setEmail(email);
                usuario.setNome((String) payload.get("name"));
                usuario.setImagem((String) payload.get("picture"));
                usuarioRepository.save(usuario);
            } else {
                usuario = usuarioOptional.get();
            }

            // Gere um novo JWT para o usuário
            String jwt = googleAuthService.generateToken(usuario);

            return ResponseEntity.ok(jwt);
        } catch (GeneralSecurityException | IOException e) {
            return ResponseEntity.status(401).body("Invalid ID token.");
        }
    }


}
