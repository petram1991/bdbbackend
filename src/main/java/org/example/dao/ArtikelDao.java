package org.example.dao;

import org.example.domain.Artikel;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ArtikelDao {
    @PersistenceContext
    private EntityManager em;

    public Object geefAlles(){
        return em.createNamedQuery("Artikel.vindAlles", Artikel.class).getResultList();
    }

    public void aanmaken(Artikel artikel){
        em.persist(artikel);
    }
}