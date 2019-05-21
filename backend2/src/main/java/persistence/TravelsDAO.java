package persistence;

import entities.Travel;
import lombok.Getter;
import lombok.Setter;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@ApplicationScoped
public class TravelsDAO {

    @PersistenceContext//handling a set of entities
    private EntityManager em;

    @Inject
    @Setter
    @Getter
    private OfficesDAO officesDAO;

    @Inject
    @Setter
    @Getter
    private EmployeesDAO employeesDAO;


    public List<Travel> loadAll() {
        return em.createNamedQuery("Travel.findAll", Travel.class).getResultList();
    }

    public List<Travel> findByDepartureOfficeId(Integer departureOfficeId) {
        return em.createNamedQuery("Travel.findByDepartureOfficeId", Travel.class).setParameter("departureOffice", officesDAO.findOne(departureOfficeId)).getResultList();
    }

    public List<Travel> findByArrivalOfficeId(Integer arrivalOfficeId) {
        return em.createNamedQuery("Travel.findByArrivalOfficeId", Travel.class).setParameter("arrivalOffice", officesDAO.findOne(arrivalOfficeId)).getResultList();
    }

    public List<Travel> findByOrganiserId(Integer organiserId) {
        return em.createNamedQuery("Travel.findByOrganiserId", Travel.class).setParameter("organiser", employeesDAO.findOne(organiserId)).getResultList();
    }


    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void persist(Travel travel){
        this.em.persist(travel);
    }

    public Travel findOne(Integer id) {
        return em.find(Travel.class, id);
    }

    public Travel update(Travel travel){
        return em.merge(travel);
    }

}
