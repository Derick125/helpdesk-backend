package com.turmab.helpdesk.security;

import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Classe utilitária para operações relacionadas a JSON Web Tokens (JWT).
 * Responsável por gerar, validar e extrair informações de tokens.
 * É marcada como @Component para ser gerenciada pelo Spring e injetada em outras classes.
 */
@Component
public class JWTUtil {

    /**
     * Tempo de expiração do token em milissegundos, injetado a partir do arquivo de propriedades.
     */
    @Value("${jwt.expiration}")
    private Long expiration;
    
    /**
     * Segredo utilizado para assinar e validar os tokens, injetado a partir do arquivo de propriedades.
     */
    @Value("${jwt.secret}")
    private String secret;

    /**
     * Gera um novo token JWT para um determinado usuário (e-mail).
     * @param email O e-mail do usuário, que será o "subject" do token.
     * @return Uma String representando o token JWT assinado.
     */
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS512, secret.getBytes())
                .compact();
    }

    /**
     * Valida um token JWT.
     * @param token A String do token a ser validado.
     * @return {@code true} se o token for válido (não expirado e com assinatura correta), {@code false} caso contrário.
     */
    public boolean tokenValido(String token) {
        Claims claims = getClaims(token);
        if (claims != null) {
            String username = claims.getSubject();
            Date expirationDate = claims.getExpiration();
            Date now = new Date(System.currentTimeMillis());

            if (username != null && expirationDate != null && now.before(expirationDate)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Extrai o nome de usuário (e-mail) de um token JWT.
     * @param token A String do token.
     * @return O nome de usuário (subject) contido no token, ou {@code null} se o token for inválido.
     */
    public String getUsername(String token) {
        Claims claims = getClaims(token);
        if (claims != null) {
            return claims.getSubject();
        }
        return null;
    }

    /**
     * Método privado para extrair as "claims" (informações) de um token.
     * Valida a assinatura do token no processo.
     * @param token A String do token.
     * @return Um objeto {@link Claims} com os dados do token, ou {@code null} se o token for inválido.
     */
    private Claims getClaims(String token) {
        try {
            return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            return null;
        }
    }
}