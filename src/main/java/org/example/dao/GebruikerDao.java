package org.example.dao;

import org.example.domain.Gebruiker;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
}
