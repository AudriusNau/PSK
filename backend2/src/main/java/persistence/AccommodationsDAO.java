package persistence;

import entities.Accommodation;
import lombok.Getter;
import lombok.Setter;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@ApplicationScoped
public class AccommodationsDAO {

    @PersistenceContext//handling a set of entities
    private EntityManager em;

    @Inject
    @Setter
    @Getter
    private RoomsDAO roomsDAO;

    @Inject
    @Setter
    @Getter
    private OfficesDAO officesDAO;


    public List<Accommodation> loadAll() {
        return em.createNamedQuery("Accommodation.findAll", Accommodation.class).getResultList();
    }

    public List<Accommodation> findByOfficeId(Integer officeId) {
        return em.createNamedQuery("Accommodation.findByOfficeId", Accommodation.class).setParameter("office", officesDAO.findOne(officeId)).getResultList();
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void persist(Accommodation accommodation){
        this.em.persist(accommodation);
    }

    public Accommodation findOne(Integer id) {
        return em.find(Accommodation.class, id);
    }

    public Accommodation update(Accommodation accommodation){
        return em.merge(accommodation);
    }

    public void delete(Accommodation accommodation)
    {
        if (!em.contains(accommodation)) em.merge(accommodation);
        em.remove(accommodation);
    }
}
