package services;

import dto.CarRentDTO;
import entities.CarRent;
import interceptors.DevbridgeInterceptor;
import lombok.Getter;
import lombok.Setter;
import persistence.CarRentsDAO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
@DevbridgeInterceptor
public class CarRentService {

    @Inject
    @Setter
    @Getter
    private CarRentsDAO carRentsDAO;

    public List<CarRent> getAll(){
        return carRentsDAO.loadAll();
    }

    public CarRent getById(Integer id){
        return carRentsDAO.findOne(id);
    }

    public CarRent create(CarRentDTO carRentDTO){
        CarRent carRent = carRentsDAO.create();
        carRent.setNeed(carRentDTO.getNeed());
        carRent.setInfo(carRentDTO.getInfo());
        carRentsDAO.persist(carRent);
        return carRent;
    }

    public CarRent update(Integer id, CarRentDTO carRentDTO){
        CarRent carRent = carRentsDAO.findOne(id);
        if (carRent == null){
            throw new IllegalArgumentException("carRent id "
                    + id + " not found");
        }
        if (carRentDTO.getNeed() != null) carRent.setNeed(carRentDTO.getNeed());
        if (carRentDTO.getInfo() != null) carRent.setInfo(carRentDTO.getInfo());
        carRentsDAO.update(carRent);
        return carRent;
    }

    public void delete(Integer id){
        CarRent carRent = carRentsDAO.findOne(id);
        carRentsDAO.delete(carRent);
    }

}
