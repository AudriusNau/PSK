package persistence;

import entities.Calendar;
import entities.EmployeeCalendar;
import lombok.Getter;
import lombok.Setter;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@ApplicationScoped
public class EmployeeCalendarsDAO {

    @PersistenceContext//handling a set of entities
    private EntityManager em;

    @Inject
    @Setter
    @Getter
    private EmployeesDAO employeesDAO;

    @Inject
    @Setter
    @Getter
    private CalendarsDAO calendarsDAO;


    public List<EmployeeCalendar> loadAll() {
        return em.createNamedQuery("EmployeeCalendar.findAll", EmployeeCalendar.class).getResultList();
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void persist(EmployeeCalendar employeeCalendar){
        this.em.persist(employeeCalendar);
    }

    /*public EmployeeCalendar findByDate(String date) {
        return em.createNamedQuery("EmployeeCalendar.findByDate", EmployeeCalendar.class).setParameter("date", date).getSingleResult();
    }

    public EmployeeCalendar findByEmployee(Integer employeeId) {
        return em.createNamedQuery("EmployeeCalendar.findByEmployee", EmployeeCalendar.class).setParameter("employeeId", employeeId).getSingleResult();
    }*/

    public List<EmployeeCalendar> findByDate(String date) {
        return em.createNamedQuery("EmployeeCalendar.findByDate", EmployeeCalendar.class).setParameter("date", calendarsDAO.findOne(date)).getResultList();
    }



    public List<EmployeeCalendar> findByEmployee(Integer employeeId) {
        return em.createNamedQuery("EmployeeCalendar.findByEmployee", EmployeeCalendar.class).setParameter("employeeId", employeesDAO.findOne(employeeId)).getResultList();
    }

    public EmployeeCalendar update(EmployeeCalendar employeeCalendar){
        return em.merge(employeeCalendar);
    }

}
