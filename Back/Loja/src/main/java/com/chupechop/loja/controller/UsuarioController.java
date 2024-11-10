package com.chupechop.loja.controller;


import com.chupechop.loja.model.DTO.DadosUsuarioDTO;
import com.chupechop.loja.model.DTO.EditarUsuarioDTO;
import com.chupechop.loja.model.DTO.RespostaEditarUsuarioDTO;
import com.chupechop.loja.model.Usuario;
import com.chupechop.loja.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PutMapping("/editar")
    public ResponseEntity<RespostaEditarUsuarioDTO> editarUsuarioLogado(@RequestBody EditarUsuarioDTO editarUsuarioDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuarioLogado = (Usuario) authentication.getPrincipal();
        Usuario usuarioAtualizado = usuarioService.editarUsuario(usuarioLogado.getId(), editarUsuarioDTO);

        RespostaEditarUsuarioDTO resposta = new RespostaEditarUsuarioDTO(
                usuarioAtualizado.getEmail(),
                usuarioAtualizado.getNome(),
                usuarioAtualizado.getCpf(),
                usuarioAtualizado.getImagem()
        );

        return ResponseEntity.ok(resposta);
    }

    @GetMapping("/dados")
    public ResponseEntity<DadosUsuarioDTO> dadosUsuarioLogado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuarioLogado = (Usuario) authentication.getPrincipal();
        return ResponseEntity.ok(new DadosUsuarioDTO(
                usuarioLogado.getNome(),
                usuarioLogado.getEmail(),
                usuarioLogado.getCpf(),
                usuarioLogado.getImagem()
        ));
    }


}
