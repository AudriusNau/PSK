package services;

import dto.AccommodationDTO;
import entities.Accommodation;
import entities.Office;
import lombok.Getter;
import lombok.Setter;
import persistence.AccommodationsDAO;
import persistence.OfficesDAO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class AccommodationService {

    @Inject
    @Setter
    @Getter
    private AccommodationsDAO accommodationsDAO;

    @Inject
    @Setter
    @Getter
    private OfficesDAO officesDAO;

    public List<Accommodation> getAll() {
        return accommodationsDAO.loadAll();
    }

    public Accommodation getById(Integer id) {
        return accommodationsDAO.findOne(id);
    }

    public List<Accommodation> getByOfficeId(Integer id) {
        return accommodationsDAO.findByOfficeId(id);
    }

    public Office getOfficeByAccommodationId(Integer id) {
        return (accommodationsDAO.findOne(id)).getOffice();
    }

    public Accommodation create(AccommodationDTO accommodationDTO) {
        Accommodation accommodation = accommodationsDAO.create();
        accommodation.setName(accommodationDTO.getName());
        accommodation.setOffice(officesDAO.findOne(accommodationDTO.getOfficeId()));
        accommodation.setAccommodationType(accommodationDTO.getAccommodationType());
        accommodationsDAO.persist(accommodation);
        return accommodation;
    }

    public Accommodation update(Integer id, AccommodationDTO accommodationDTO) {
        Accommodation accommodation = accommodationsDAO.findOne(id);
        if (accommodation == null){
            throw new IllegalArgumentException("accommodation id "
                    + id + " not found");
        }
        if (accommodationDTO.getName() != null) accommodation.setName(accommodationDTO.getName());
        if (accommodationDTO.getOfficeId() != null) accommodation.setOffice(officesDAO.findOne(accommodationDTO.getOfficeId()));
        if (accommodationDTO.getAccommodationType() != null) accommodation.setAccommodationType(accommodationDTO.getAccommodationType());
        accommodationsDAO.update(accommodation);
        return accommodation;
    }

    public void delete(Integer id) {
        Accommodation accommodation = accommodationsDAO.findOne(id);
        accommodationsDAO.delete(accommodation);
    }
}
