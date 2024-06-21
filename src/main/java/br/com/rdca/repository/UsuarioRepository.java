package br.com.rdca.repository;

import br.com.rdca.entity.Usuario;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UsuarioRepository implements PanacheRepository<Usuario> {

    public Usuario findByNomeUsuario(String nomeUsuario) {
        return find("nome_usuario", nomeUsuario).firstResult();
    }

}
