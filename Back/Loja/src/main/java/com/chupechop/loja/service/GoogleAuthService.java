package com.chupechop.loja.service;

import com.chupechop.loja.model.Usuario;
import com.chupechop.loja.repository.UsuarioRepository;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.*;

@Service
public class GoogleAuthService {

    @Value("${google.client.client-id}")
    private String clientId;

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.expiration-time}")
    private long jwtExpiration;

    private final UsuarioRepository usuarioRepository;

    public GoogleAuthService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public String verifyGoogleToken(String idTokenString) throws GeneralSecurityException, IOException {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())
                .setAudience(Collections.singletonList(clientId))
                .build();

        GoogleIdToken idToken = verifier.verify(idTokenString);
        if (idToken != null) {
            GoogleIdToken.Payload payload = idToken.getPayload();
            String email = payload.getEmail();

            // Aqui você pode buscar o usuário no seu banco de dados usando o email
            Usuario usuario = findUserByEmail(email);

            // Gere um novo JWT para o seu backend
            return generateToken(usuario);
        } else {
            throw new GeneralSecurityException("Invalid ID token.");
        }
    }

    private Usuario findUserByEmail(String email) {
        return usuarioRepository.findByEmail(email).orElseThrow(() -> new NoSuchElementException("No user found with email: " + email));
    }


    public String generateToken(UserDetails userDetails) {
        Usuario usuario = (Usuario) userDetails;
        System.out.println(usuario);
        Map<String, Object> claims = new HashMap<>();
        claims.put("nome", usuario.getNome());
        claims.put("sub", usuario.getUsername());
        claims.put("imagem", usuario.getImagem());
        claims.put("iat", new Date().getTime() / 1000); // Issued at
        System.out.println(claims);
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public GoogleIdToken.Payload getPayload(String idTokenString) throws GeneralSecurityException, IOException {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())
                .setAudience(Collections.singletonList(clientId))
                .build();

        GoogleIdToken idToken = verifier.verify(idTokenString);
        if (idToken != null) {
            return idToken.getPayload();
        } else {
            throw new GeneralSecurityException("Invalid ID token.");
        }
    }
}