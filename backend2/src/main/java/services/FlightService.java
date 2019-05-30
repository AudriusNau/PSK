package services;

import dto.FlightDTO;
import entities.Flight;
import lombok.Getter;
import lombok.Setter;
import persistence.FlightsDAO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class FlightService {

    @Inject
    @Setter
    @Getter
    private FlightsDAO flightsDAO;

    public List<Flight> getAll(){
        return flightsDAO.loadAll();
    }

    public Flight getById(Integer id){
        return flightsDAO.findOne(id);
    }

    public Flight create(FlightDTO flightDTO){
        Flight flight = flightsDAO.create();
        flight.setNeed(flightDTO.getNeed());
        flight.setInfo(flightDTO.getInfo());
        flightsDAO.persist(flight);
        return flight;
    }

    public Flight update(Integer id, FlightDTO flightDTO){
        Flight flight = flightsDAO.findOne(id);
        if (flight == null){
            throw new IllegalArgumentException("flight id "
                    + id + " not found");
        }
        if (flightDTO.getNeed() != null) flight.setNeed(flightDTO.getNeed());
        if (flightDTO.getInfo() != null) flight.setInfo(flightDTO.getInfo());
        flightsDAO.update(flight);
        return flight;
    }

    public void delete(Integer id){
        Flight flight = flightsDAO.findOne(id);
        flightsDAO.delete(flight);
    }

}
