package br.com.rdca.repository;

import br.com.rdca.entity.Usuario;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class UsuarioRepository implements PanacheRepository<Usuario> {

    public Usuario findByNomeUsuario(String nomeUsuario) {
        return find("nomeUsuario", nomeUsuario).firstResult();
    }

    @Transactional
    public Response addUsuario(Usuario usuario){
        Usuario.persist(usuario);
        return Response.ok(usuario).status(201).build();
    }

}
