package com.packtpub.javaee8;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.Map;

@RequestScoped
@Path("version")
public class VersionResource {

    /**
     * MediaType implementation for the version resource in v1.
     */
    public static final MediaType V1 = new MediaType("application", "vnd.version.v1+json");

    /**
     * MediaType implementation for the version resource in v2.
     */
    public static final MediaType V2 = new MediaType("application", "vnd.version.v2+json");

    @GET
    @Produces("application/vnd.version.v2+json")
    public Response v2() {
        Map<String, String> version = Collections.singletonMap("version", "v2");
        return Response.ok(version).build();
    }

    @GET
    @Produces({"application/json; qs=0.75", "application/vnd.version.v1+json; qs=1.0"})
    public Response v1() {
        Map<String, String> version = Collections.singletonMap("version", "v1");
        return Response.ok(version).build();
    }

}
