package persistence;

import entities.Calendar;
import entities.RoomCalendar;
import lombok.Getter;
import lombok.Setter;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@ApplicationScoped
public class RoomCalendarsDAO {

    @PersistenceContext//handling a set of entities
    private EntityManager em;

    @Inject
    @Setter
    @Getter
    private RoomsDAO roomsDAO;

    @Inject
    @Setter
    @Getter
    private CalendarsDAO calendarsDAO;


    public List<RoomCalendar> loadAll() {
        return em.createNamedQuery("RoomCalendar.findAll", RoomCalendar.class).getResultList();
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void persist(RoomCalendar roomCalendar){
        this.em.persist(roomCalendar);
    }

    /*public RoomCalendar findByDate(String date) {
        return em.createNamedQuery("RoomCalendar.findByDate", RoomCalendar.class).setParameter("date", date).getSingleResult();
    }

    public RoomCalendar findByRoom(Integer roomId) {
        return em.createNamedQuery("RoomCalendar.findByRoom", RoomCalendar.class).setParameter("roomId", roomId).getSingleResult();
    }*/

    public List<RoomCalendar> findByDate(String date) {
        return em.createNamedQuery("RoomCalendar.findByDate", RoomCalendar.class).setParameter("date", calendarsDAO.findOne(date)).getResultList();
    }



    public List<RoomCalendar> findByRoom(Integer roomId) {
        return em.createNamedQuery("RoomCalendar.findByRoom", RoomCalendar.class).setParameter("roomId", roomsDAO.findOne(roomId)).getResultList();
    }

    public RoomCalendar update(RoomCalendar roomCalendar){
        return em.merge(roomCalendar);
    }

}