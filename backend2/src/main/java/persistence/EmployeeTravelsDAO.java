package persistence;

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
    private TravelsDAO travelsDAO;

    @Inject
    @Setter
    @Getter
    private FlightsDAO flightsDAO;

    @Inject
    @Setter
    @Getter
    private CarRentsDAO carRentsDAO;

    @Inject
    @Setter
    @Getter
    private RoomsDAO roomsDAO;


    public List<EmployeeTravel> loadAll() {
        return em.createNamedQuery("EmployeeTravel.findAll", EmployeeTravel.class).getResultList();
    }

    public List<EmployeeTravel> loadAllPendingTravels() {
        return em.createNamedQuery("EmployeeTravel.findAllPendingTravels", EmployeeTravel.class).getResultList();
    }

    public List<EmployeeTravel> loadAllPendingTravelsForEmployee(Integer employeeId) {
        return em.createNamedQuery("EmployeeTravel.findAllPendingTravelsForEmployee", EmployeeTravel.class).setParameter("employee", employeesDAO.findOne(employeeId)).getResultList();
    }

    public List<EmployeeTravel> loadAllAcceptedTravelsForEmployee(Integer employeeId) {
        return em.createNamedQuery("EmployeeTravel.findAllAcceptedTravelsForEmployee", EmployeeTravel.class).setParameter("employee", employeesDAO.findOne(employeeId)).getResultList();
    }

    public EmployeeTravel create(){return new EmployeeTravel();}

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void persist(EmployeeTravel employeeTravel){
        this.em.persist(employeeTravel);
    }

    public EmployeeTravel findOne(Integer id) {
        return em.find(EmployeeTravel.class, id);
    }

    public List<EmployeeTravel> findByTravelId(Integer travelId) {
        return em.createNamedQuery("EmployeeTravel.findByTravelId", EmployeeTravel.class).setParameter("travel", travelsDAO.findOne(travelId)).getResultList();
    }

    public List<EmployeeTravel> findByEmployee(Integer employeeId) {
        return em.createNamedQuery("EmployeeTravel.findByEmployee", EmployeeTravel.class).setParameter("employee", employeesDAO.findOne(employeeId)).getResultList();
    }

    public EmployeeTravel findByEmployeeAndTravel(Integer employeeId, Integer travelId) {
        try {
            return em.createNamedQuery("EmployeeTravel.findByEmployeeAndTravel", EmployeeTravel.class).setParameter("employee", employeesDAO.findOne(employeeId)).setParameter("travel", travelsDAO.findOne(travelId)).getSingleResult();
        }catch (Exception e){
            return null;
        }
    }

    public List<EmployeeTravel> findByFlight(Integer flightId) {
        return em.createNamedQuery("EmployeeTravel.findByFlight", EmployeeTravel.class).setParameter("flight", flightsDAO.findOne(flightId)).getResultList();
    }

    public List<EmployeeTravel> findByCarRent(Integer carRentId) {
        return em.createNamedQuery("EmployeeTravel.findByCarRent", EmployeeTravel.class).setParameter("carRent", carRentsDAO.findOne(carRentId)).getResultList();
    }

    public List<EmployeeTravel> findByRoom(Integer roomId) {
        return em.createNamedQuery("EmployeeTravel.findByRoom", EmployeeTravel.class).setParameter("room", roomsDAO.findOne(roomId)).getResultList();
    }

    public EmployeeTravel update(EmployeeTravel employeeTravel){
        return em.merge(employeeTravel);
    }

    public void delete(EmployeeTravel employeeTravels)
    {
        if (!em.contains(employeeTravels)) em.merge(employeeTravels);
        em.remove(employeeTravels);
    }
}
