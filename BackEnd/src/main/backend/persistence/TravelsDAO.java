package main.backend.persistence;

import main.backend.entities.Travel;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@ApplicationScoped
public class TravelsDAO {
    @PersistenceContext
    private EntityManager em;

    public List<Travel> loadAll(){
        return em.createNamedQuery("Travel.findAll", Travel.class).getResultList();
    }
    public void setEm(EntityManager  em){
        this.em=em;
    }
    public void persist(Travel travel){
        this.em.persist(travel);
    }
}
