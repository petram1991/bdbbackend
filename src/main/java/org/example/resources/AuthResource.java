package org.example.resources;


import org.example.dao.GebruikerDao;
import org.example.domain.Gebruiker;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Produces(MediaType.APPLICATION_JSON)
@Path("/auth")
public class AuthResource {

    @Inject private GebruikerDao dao;

    @GET
    public Response geefalles(){
        return Response.ok(dao.geefAlles()).build();
    }

    @GET
    @Path("{id}")
    public Response geefGebruiker(@PathParam("id") Long id){
        Gebruiker gebruiker = dao.vindBijId(id);

        return Response.ok(gebruiker).build();
    }

    @Path("/registreren")
    @POST
    public Response aanmaken(Gebruiker gebruiker){
        dao.aanmaken(gebruiker);
        return Response.ok().build();
    }
}
