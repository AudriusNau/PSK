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

    public Employee findByUsernameAndPassword(String username, String password){
        return em.createNamedQuery("Employee.findByUsernameAndPassword", Employee.class).setParameter("username", username).setParameter("password", password).getSingleResult();
    }

    public Employee findByUsername(String username){
        return em.createNamedQuery("Employee.findEmployee", Employee.class).setParameter("username", username).getSingleResult();
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void persist(Employee employee){
        this.em.persist(employee);
    }

    public Employee findOne(Integer id) {
        return em.find(Employee.class, id);
    }

    public Employee update(Employee employee){
        return em.merge(employee);
    }

    public void delete(Employee employee)
    {
        if (!em.contains(employee)) em.merge(employee);
        em.remove(employee);
    }
}
