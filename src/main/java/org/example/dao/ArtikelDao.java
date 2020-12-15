package org.example.dao;

import org.example.domain.Artikel;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ArtikelDao {
    @PersistenceContext
    private EntityManager em;

    public Artikel vindBijId(long id){
        return em.find(Artikel.class, id);
    }

    public Object geefAlles(){
        return em.createNamedQuery("Artikel.vindAlles", Artikel.class).getResultList();
    }

    public void aanmaken(Artikel artikel){
        em.persist(artikel);
    }

   public void update(Artikel artikel) {
       em.merge(artikel);
   }

}
