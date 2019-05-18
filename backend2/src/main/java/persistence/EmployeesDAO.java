package persistence;

import entities.Employee;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@ApplicationScoped
public class EmployeesDAO {

    @PersistenceContext//handling a set of entities
    private EntityManager em;


    public List<Employee> loadAll() {
        return em.createNamedQuery("Employee.findAll", Employee.class).getResultList();
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void persist(Employee bookstore){
        this.em.persist(bookstore);
    }

    public Employee findOne(Integer id) {
        return em.find(Employee.class, id);
    }

    public Employee update(Employee bookstore){
        return em.merge(bookstore);
    }

}
