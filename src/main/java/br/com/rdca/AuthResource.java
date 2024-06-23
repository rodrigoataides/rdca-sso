package br.com.rdca;

import java.util.HashMap;
import java.util.Map;

import org.jose4j.json.internal.json_simple.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import br.com.rdca.entity.Usuario;
import br.com.rdca.repository.UsuarioRepository;
import br.com.rdca.security.Roles;
import br.com.rdca.security.TokenService;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.resource.spi.work.SecurityContext;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/auth")
public class AuthResource {

    @Context
    SecurityContext securityContext;

    @Inject
    TokenService service;

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    SecurityIdentity identity;

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response login(Usuario credenciais) throws JsonProcessingException {
        Usuario user = usuarioRepository.findByNomeUsuario(credenciais.getNomeUsuario());
        if (user != null && user.getSenha().equals(credenciais.getSenha())) { 

            String token = service.generateUserToken(credenciais.getNomeUsuario(), credenciais.getNomeUsuario());//jwtUtils.generateToken(user.getNomeUsuario());
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            ObjectMapper om = new ObjectMapper();
            String json = om.writeValueAsString(user);
            response.put("user", json);
            return Response.ok(response).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    @GET
    @Path("/validate")
    //@Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({Roles.USER, Roles.SERVICE})
    public Response validate(@QueryParam("token") String token) throws JsonProcessingException {
        Map<String, String> response = new HashMap<>();

        //Carregando o usuário autenticado.
        String username = identity.getPrincipal().getName();
        Usuario usuario = usuarioRepository.findByNomeUsuario(username);
        ObjectMapper om = new ObjectMapper();
        String jsonUser = om.writeValueAsString(usuario);
        response.put("user", jsonUser);

        //Buscar mais dados dos usuário, tipo sistema que ele pertence, role e expiracao.
        //Para buscar preciso que seja criado os dados do sistema e clientes com as permissoes.

        return Response.ok(response).build();
    }

    @POST
    @Path("/logout")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response logout(String token) {
        // Invalidar o token (JWT é stateless, então geralmente a invalidção é feita no
        // cliente)
        return Response.ok().build();
    }

    @POST
    @Path("/add")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({Roles.USER, Roles.SERVICE})
    public Response add(Usuario usuario) {
        //return Response.ok().status(200).build();
        /*@Context HttpHeaders headers;
        String token = @getRequestHeader("Token"); //@HeaderParam('Token');
        if(validate(token).status == 200){*/
        return usuarioRepository.addUsuario(usuario);
        /*}else{
            return Response.ok().status(401).build();
        }*/
    }

}
