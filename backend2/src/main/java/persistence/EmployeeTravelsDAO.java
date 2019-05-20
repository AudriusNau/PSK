package persistence;

import entities.Travel;
import entities.EmployeeTravel;
import lombok.Getter;
import lombok.Setter;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@ApplicationScoped
public class EmployeeTravelsDAO {

    @PersistenceContext//handling a set of entities
    private EntityManager em;

    @Inject
    @Setter
    @Getter
    private EmployeesDAO employeesDAO;

    @Inject
    @Setter
    @Getter
    private TravelsDAO calendarsDAO;


    public List<EmployeeTravel> loadAll() {
        return em.createNamedQuery("EmployeeTravel.findAll", EmployeeTravel.class).getResultList();
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void persist(EmployeeTravel employeeTravel){
        this.em.persist(employeeTravel);
    }

    /*public EmployeeTravel findByTravelId(String travelId) {
        return em.createNamedQuery("EmployeeTravel.findByTravelId", EmployeeTravel.class).setParameter("travelId", travelId).getSingleResult();
    }

    public EmployeeTravel findByEmployee(Integer employeeId) {
        return em.createNamedQuery("EmployeeTravel.findByEmployee", EmployeeTravel.class).setParameter("employeeId", employeeId).getSingleResult();
    }*/

    public EmployeeTravel findOne(Integer id) {
        return em.find(EmployeeTravel.class, id);
    }

    public List<EmployeeTravel> findByTravelId(Integer travelId) {
        return em.createNamedQuery("EmployeeTravel.findByTravelId", EmployeeTravel.class).setParameter("travel", calendarsDAO.findOne(travelId)).getResultList();
    }



    public List<EmployeeTravel> findByEmployee(Integer employeeId) {
        return em.createNamedQuery("EmployeeTravel.findByEmployee", EmployeeTravel.class).setParameter("employee", employeesDAO.findOne(employeeId)).getResultList();
    }

    public EmployeeTravel update(EmployeeTravel employeeTravel){
        return em.merge(employeeTravel);
    }

}