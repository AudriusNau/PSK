package persistence;

import entities.Office;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@ApplicationScoped
public class OfficesDAO {

    @PersistenceContext//handling a set of entities
    private EntityManager em;


    public List<Office> loadAll() {
        return em.createNamedQuery("Office.findAll", Office.class).getResultList();
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void persist(Office office){
        this.em.persist(office);
    }

    public Office create(){return new Office();}

    public Office findOne(Integer id) {
        return em.find(Office.class, id);
    }

    public Office update(Office office){
        return em.merge(office);
    }

    public void delete(Office office)
    {
        if (!em.contains(office)) em.merge(office);
        em.remove(office);
    }

}
