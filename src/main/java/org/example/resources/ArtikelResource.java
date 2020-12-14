package org.example.resources;

import org.example.dao.ArtikelDao;
import org.example.domain.Artikel;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Produces(MediaType.APPLICATION_JSON)
@Path("/advertenties")
public class ArtikelResource {

    @Inject
    private ArtikelDao dao;

    @GET
    public Response alleAdvertenties(){
        return Response.ok(dao.geefAlles()).build();
    }

    @Path("/nieuw")
    @POST
    public Artikel aanmakenArtikel(Artikel artikel){
        dao.aanmaken(artikel);
        return artikel;
    }


}
