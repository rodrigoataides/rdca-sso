package br.com.rdca;

import java.util.HashMap;
import java.util.Map;

import br.com.rdca.entity.User;
import br.com.rdca.repository.UserRepository;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/auth")
public class AuthResource {

    @Inject
    JwtUtils jwtUtils;

    @Inject
    UserRepository userRepository;

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(UserCredentials credentials) {

        User user = userRepository.findByUsername(credentials.getUsername());
        if (user != null && user.getPassword().equals(credentials.getPassword())) {
            String token = jwtUtils.generateToken(user.getUsername);
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            return Response.ok(response).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    @POST
    @Path("/validade")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response validate(String token) {
        //Validar o token JWT (o próprio Quarkus cuidará da validação se configurado corretamente)
        return Response.ok().build();
    }

    @POST
    @Path("/logout")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response logout(String token) {
        // Invalidar o token (JWT é stateless, então geralmente a invalidção é feita no cliente)
        return Response.ok().build();
    }

}
