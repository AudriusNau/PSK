package services;

import dto.OfficeDTO;
import entities.Office;
import lombok.Getter;
import lombok.Setter;
import persistence.OfficesDAO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class OfficeService {

    @Inject
    @Getter
    @Setter
    OfficesDAO officesDAO;

    public List<Office> getAll(){
        return officesDAO.loadAll();
    }

    public Office getById(Integer id){
        return officesDAO.findOne(id);
    }

    public Office create(OfficeDTO officeDTO){
        Office office = officesDAO.create();
        office.setName(officeDTO.getName());
        officesDAO.persist(office);
        return office;
    }

    public Office update(Integer id, OfficeDTO officeDTO){
        Office office = officesDAO.findOne(id);
        if (office == null){
            throw new IllegalArgumentException("office id "
                    + id + " not found");
        }
        if (officeDTO.getName() != null) office.setName(officeDTO.getName());
        officesDAO.update(office);
        return office;
    }

    public void delete(Integer id){
        Office office = officesDAO.findOne(id);
        officesDAO.delete(office);
    }

}
