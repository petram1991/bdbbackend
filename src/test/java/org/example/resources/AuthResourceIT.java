package org.example.resources;

import org.example.App;
import org.example.dao.GebruikerDao;
import org.example.domain.Gebruiker;
import org.example.util.KeyGenerator;
import org.example.util.SimpleKeyGenerator;
import org.example.util.WachtwoordUtils;
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

    private static String authResource;
    private static String gebruikersUri = "resources/auth";


    @Before
    public void setup(){
        authResource = deploymentUrl + gebruikersUri;
    }

    @Deployment
    public static Archive<?> createDeployment() {
        WebArchive archive = ShrinkWrap.create(WebArchive.class,"AuthResourceIT.war")
                .addClass(App.class)
                .addClass(AuthResource.class)
                .addClass(Gebruiker.class)
                .addClass(GebruikerDao.class)
                .addClass(KeyGenerator.class)
                .addClass(SimpleKeyGenerator.class)
                .addClass(WachtwoordUtils.class);

        System.out.println(archive.toString(true));
        return archive;
    }

    @Test
    public void geefalles() {
        Client http = ClientBuilder.newClient();
       Gebruiker gebruiker = Gebruiker.builder().id(1).gebruiksnaam("tom").email("test@novi.nl").wachtwoord("test123").build();

       String postGebruiker = http
               .target(authResource)
               .request().post(entity(gebruiker, APPLICATION_JSON), String.class);
        System.out.println(postGebruiker);

        String allContacts = http
                .target(authResource)
                .request().get(String.class);

        System.out.println(allContacts);

        assertThat(allContacts, containsString("\"id\":\"1\""));
        assertThat(allContacts, containsString("\"gebruiksnaam\":\"tom\""));
        assertThat(allContacts, containsString("\"email\":\"test@novi.nl\""));
        assertThat(allContacts, containsString("\"wachtwoord\":\"test123\""));
    }
}
