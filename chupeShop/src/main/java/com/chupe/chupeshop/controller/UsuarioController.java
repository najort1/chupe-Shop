package com.chupe.chupeshop.controller;

import com.chupe.chupeshop.model.DTO.UsuarioLoginDTO;
import com.chupe.chupeshop.model.DTO.UsuarioRegistradoDTO;
import com.chupe.chupeshop.model.DTO.UsuarioRegistrarDTO;
import com.chupe.chupeshop.model.DTO.UsuarioRespostaLogin;
import com.chupe.chupeshop.model.Usuario;
import com.chupe.chupeshop.service.AuthenticationService;
import com.chupe.chupeshop.service.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UsuarioController {

    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    public UsuarioController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<UsuarioRespostaLogin> loginUsuario(@RequestBody UsuarioRegistrarDTO usuario) {
        UsuarioLoginDTO usuarioLogin = new UsuarioLoginDTO(usuario.email(), usuario.senha());
        Usuario usuarioLogado = authenticationService.loginUsuario(usuarioLogin);
        String token = jwtService.generateToken(usuarioLogado);
        UsuarioRespostaLogin usuarioRespostaLogin = new UsuarioRespostaLogin(
                usuarioLogado.getNome(),
                usuarioLogado.getEmail(),
                usuarioLogado.getCpf(),
                usuarioLogado.getTelefone(),
                usuarioLogado.getEndereco(),
                token
        );
        return ResponseEntity.ok(usuarioRespostaLogin);
    }

    @PostMapping("/registrar")
    public ResponseEntity<UsuarioRegistradoDTO> registrarUsuario(@RequestBody @Valid UsuarioRegistrarDTO usuario) {
        System.out.println(usuario);
        Usuario usuarioRegistrado = authenticationService.registrarUsuario(usuario);
        System.out.println(usuarioRegistrado);
        UsuarioRegistradoDTO usuarioRegistradoDTO = new UsuarioRegistradoDTO(
                usuarioRegistrado.getNome(),
                usuarioRegistrado.getEmail(),
                usuarioRegistrado.getCpf(),
                usuarioRegistrado.getTelefone(),
                usuarioRegistrado.getEndereco()
        );
        return ResponseEntity.ok(usuarioRegistradoDTO);
    }

}
