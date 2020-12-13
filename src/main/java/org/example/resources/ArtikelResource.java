package org.example.resources;


import org.example.dao.ArtikelDao;
import org.example.domain.Artikel;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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
    public Response aanmakenArtikel(Artikel artikel){
        dao.aanmaken(artikel);
        return Response.ok().build();
    }

}
