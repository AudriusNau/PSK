package persistence;

import entities.Flight;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@ApplicationScoped
public class FlightsDAO {

    @PersistenceContext//handling a set of entities
    private EntityManager em;


    public List<Flight> loadAll() {
        return em.createNamedQuery("Flight.findAll", Flight.class).getResultList();
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void persist(Flight flight){
        this.em.persist(flight);
    }

    public Flight findOne(Integer id) {
        return em.find(Flight.class, id);
    }

    public Flight update(Flight flight){
        return em.merge(flight);
    }

    public Flight create(){return new Flight();}

    public void delete(Flight flight)
    {
        if (!em.contains(flight)) em.merge(flight);
        em.remove(flight);
    }

}
