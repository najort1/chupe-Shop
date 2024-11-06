package com.chupechop.loja.controller;

import com.chupechop.loja.model.DTO.CadastroUsuarioDTO;
import com.chupechop.loja.model.DTO.LoginUsuarioDTO;
import com.chupechop.loja.model.DTO.RespostaLoginDTO;
import com.chupechop.loja.service.AuthenticationService;
import com.chupechop.loja.service.JwtService;
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


}
