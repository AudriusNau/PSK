package persistence;

import entities.CarRent;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@ApplicationScoped
public class CarRentsDAO {

    @PersistenceContext//handling a set of entities
    private EntityManager em;

    public CarRent create(){return new CarRent();}

    public List<CarRent> loadAll() {
        return em.createNamedQuery("CarRent.findAll", CarRent.class).getResultList();
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void persist(CarRent carRent){
        this.em.persist(carRent);
    }

    public CarRent findOne(Integer id) {
        return em.find(CarRent.class, id);
    }

    public CarRent update(CarRent carRent){
        return em.merge(carRent);
    }

    public void delete(CarRent carRent)
    {
        if (!em.contains(carRent)) em.merge(carRent);
        em.remove(carRent);
    }

}
