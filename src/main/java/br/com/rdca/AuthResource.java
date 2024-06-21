package br.com.rdca;

import java.util.HashMap;
import java.util.Map;

import br.com.rdca.entity.Usuario;
import br.com.rdca.repository.UsuarioRepository;
import br.com.rdca.utils.JwtUtils;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/auth")
public class AuthResource {

    @Inject
    JwtUtils jwtUtils;

    @Inject
    UsuarioRepository usuarioRepository;

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(UsuarioCredenciais credenciais) {
        System.out.print(credenciais);
        Usuario user = usuarioRepository.findByNomeUsuario(credenciais.getNomeUsuario());
        if (user != null && user.getSenha().equals(credenciais.getSenha())) {
            String token = jwtUtils.generateToken(user.getNomeUsuario());
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            return Response.ok(response).build();
        } else {
            return Response.ok(credenciais).build();// Response.status(Response.Status.ACCEPTED).build();
        }
    }

    @POST
    @Path("/validade")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response validate(String token) {
        // Validar o token JWT (o próprio Quarkus cuidará da validação se configurado
        // corretamente)
        return Response.ok().build();
    }

    @POST
    @Path("/logout")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response logout(String token) {
        // Invalidar o token (JWT é stateless, então geralmente a invalidção é feita no
        // cliente)
        return Response.ok().build();
    }

}
