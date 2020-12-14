package org.example.resources;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.example.dao.GebruikerDao;
import org.example.domain.Gebruiker;
import org.example.util.KeyGenerator;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static java.time.LocalDateTime.now;
import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

@Produces(MediaType.APPLICATION_JSON)
@Path("/auth")
public class AuthResource {

    @Inject private GebruikerDao dao;

    @Inject private KeyGenerator keyGenerator;

    @Context
    private UriInfo uriInfo;

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
    public Response aanmakenGebruiker(Gebruiker gebruiker){
        dao.aanmaken(gebruiker);
        return Response.ok().build();
    }

    @Path("/inloggen")
    @POST
    public Response authenticateGebruiker(Gebruiker gebruiker){
        try{
            String gebruiksnaam = gebruiker.getGebruiksnaam();
            String wachtwoord = gebruiker.getWachtwoord();

            dao.authenticate(gebruiksnaam, wachtwoord);
            String token = issueToken(gebruiksnaam);

            return Response.ok().header(AUTHORIZATION, "Bearer " + token).build();
        } catch (Exception e) {
            return Response.status(UNAUTHORIZED).build();
        }
    }
    private String issueToken(String login) {
        Key key = keyGenerator.generateKey();
        String jwtToken = Jwts.builder()
                .setSubject(login)
                .setIssuer(uriInfo.getAbsolutePath().toString())
                .setIssuedAt(new Date())
                .setExpiration(toDate(now().plusMinutes(15L)))
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
        System.out.println("#### generating token for a key : " + jwtToken + " - " + key);
        return jwtToken;
    }

    private Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
