package br.com.rdca.utils;

import java.util.HashSet;
import java.util.Set;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class JwtUtils {
    public String generateToken(String username) {
        Set<String> groups = new HashSet<>();
        groups.add("User"); // ou outras roles/grupos
        return Jwt.issuer("https://sso.rdca.com.br/issuer")
                .upn(username)
                .groups(groups)
                .expiresIn(3600) // 1 hora
                .sign();
    }
}
