package org.example.resources;

import org.example.App;
import org.example.domain.Gebruiker;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import java.net.URL;

import static javax.ws.rs.client.Entity.entity;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(Arquillian.class)
public class AuthResourceIT {

    @ArquillianResource
    private URL deploymentUrl;
    private String authResource;
    private String gebruikersUri = "resources/auth";


    @Before
    public void setup(){
        authResource = deploymentUrl + gebruikersUri;
    }

    @Deployment
    public static Archive<?> createDeployment() {
        WebArchive archive = ShrinkWrap.create(WebArchive.class,"AuthResourceIT.war")
                .addPackages(true, App.class.getPackage())
                .addAsWebInfResource("test-beans.xml", "beans.xml")
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml");


        System.out.println(archive.toString(true));
        return archive;
    }

    @Test
    public void geefalles() {
        Client http = ClientBuilder.newClient();
       Gebruiker gebruiker = Gebruiker.builder().gebruiksnaam("tom").email("test@novi.nl").build();

       String postedGebruiker = http
               .target(authResource)
               .request().post(entity(gebruiker, APPLICATION_JSON), String.class);

       System.out.println(postedGebruiker);

        String allGebruikers = http
                .target(authResource)
                .request().get(String.class);

        assertThat(allGebruikers, containsString("\"gebruiksnaam\":\"tom\""));
        assertThat(allGebruikers, containsString("\"email\":\"test@novi.nl\""));

    }
}
