package com.packtpub.javaee8.jwt;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.Map;

@RequestScoped
@Path("/jwt")
@JwtAuthz
public class AuthenticationResource {

    @Inject
    private DecodedJWT decodedJWT;

    @GET
    @Path("/authenticate")
    public Response authenticate() {
        Map<String, Claim> claims = decodedJWT.getClaims();

        JsonObject response = Json.createObjectBuilder()
                .add("name", claims.get("name").asString())
                .add("subject", claims.get("sub").asString())
                .build();

        return Response.ok(response).build();
    }
}
