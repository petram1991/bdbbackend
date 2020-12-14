package org.example.dao;

import org.example.domain.Gebruiker;
import org.example.util.WachtwoordUtils;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class GebruikerDao {
    @PersistenceContext
    private EntityManager em;

    public void aanmaken(Gebruiker gebruiker){
        em.persist(gebruiker);
    }

    public List geefAlles() {
        return em.createNamedQuery("Gebruiker.vindAlles", Gebruiker.class).getResultList();
    }

    public Gebruiker vindBijId(long id){
        return em.find(Gebruiker.class, id);
    }

    public void update(Gebruiker gebruiker){
        em.merge(gebruiker);
    }

    public void authenticate(String gebruiksnaam, String wachtwoord) {
        TypedQuery<Gebruiker> query = em.createNamedQuery(Gebruiker.VIND_GEBRUIKER_WACHTWOORD, Gebruiker.class);
        query.setParameter("gebruiksnaam", gebruiksnaam);
        query.setParameter("wachtwoord", WachtwoordUtils.digestPassword(wachtwoord));
        Gebruiker gebruiker = query.getSingleResult();

        if (gebruiker == null) throw new SecurityException("Verkeerde gebruiksnaam/wachtwoord");
    }
}
