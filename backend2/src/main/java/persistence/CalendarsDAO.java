package persistence;

import entities.Calendar;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@ApplicationScoped
public class CalendarsDAO {

    @PersistenceContext//handling a set of entities
    private EntityManager em;


    public List<Calendar> loadAll() {
        return em.createNamedQuery("Calendar.findAll", Calendar.class).getResultList();
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void persist(Calendar calendar){
        this.em.persist(calendar);
    }

    public Calendar findOne(String date) {
        return em.find(Calendar.class, date);
    }

    public Calendar update(Calendar calendar){
        return em.merge(calendar);
    }
    
}
