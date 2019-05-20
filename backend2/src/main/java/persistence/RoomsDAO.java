package persistence;

import entities.Accommodation;
import entities.Room;
import lombok.Getter;
import lombok.Setter;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@ApplicationScoped
public class RoomsDAO {

    @PersistenceContext//handling a set of entities
    private EntityManager em;

    @Inject
    @Setter
    @Getter
    private AccommodationsDAO accommodationsDAO;


    public List<Room> loadAll() {
        return em.createNamedQuery("Room.findAll", Room.class).getResultList();
    }

    public List<Room> findByAccommodationId(Integer accommodationId) {
        return em.createNamedQuery("Room.findByAccommodationId", Room.class).setParameter("accommodation", accommodationsDAO.findOne(accommodationId)).getResultList();
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void persist(Room room){
        this.em.persist(room);
    }

    public Room findOne(Integer id) {
        return em.find(Room.class, id);
    }

    public Room update(Room room){
        return em.merge(room);
    }

}
