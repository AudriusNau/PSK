package services;

import dto.EmployeeTravelDTO;
import dto.MergeTravelsDTO;
import entities.*;
import interceptors.DevbridgeInterceptor;
import lombok.Getter;
import lombok.Setter;
import persistence.*;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Date;
import java.util.List;

@ApplicationScoped
@DevbridgeInterceptor
public class EmployeeTravelService {

    @Inject
    @Setter
    @Getter
    private EmployeeTravelsDAO employeeTravelsDAO;

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

    @Inject
    @Setter
    @Getter
    private AccommodationsDAO accommodationsDAO;

    @Inject
    private AccommodationService accommodationService;

    @Inject
    private CalendarService calendarService;

    public List<EmployeeTravel> getAll(){
        return employeeTravelsDAO.loadAll();
    }

    public EmployeeTravel getByEmployeeTravelId(Integer id){
        return employeeTravelsDAO.findOne(id);
    }

    public List<EmployeeTravel> getByTravelId(Integer id){
        return employeeTravelsDAO.findByTravelId(id);
    }

    public List<EmployeeTravel> getByEmployeeId(Integer id){
        return employeeTravelsDAO.findByEmployee(id);
    }

    public List<EmployeeTravel> getPendingTravelsForEmployee(Integer id){
        return employeeTravelsDAO.loadAllPendingTravelsForEmployee(id);
    }

    public List<EmployeeTravel> getAcceptedTravelsForEmployee(Integer id){
        return employeeTravelsDAO.loadAllAcceptedTravelsForEmployee(id);
    }

    public List<EmployeeTravel> getByFlight(Integer id){
        return employeeTravelsDAO.findByFlight(id);
    }

    public List<EmployeeTravel> getByCarRent(Integer id){
        return employeeTravelsDAO.findByCarRent(id);
    }

    public List<EmployeeTravel> getByRoom(Integer id){
        return employeeTravelsDAO.findByRoom(id);
    }

    public Flight getFlight(Integer id){
        return (employeeTravelsDAO.findOne(id)).getFlight();
    }

    public CarRent getCarRent(Integer id){
        return (employeeTravelsDAO.findOne(id)).getCarRent();
    }

    public Room getRoom(Integer id){
        return (employeeTravelsDAO.findOne(id)).getRoom();
    }

    public EmployeeTravel create(EmployeeTravelDTO employeeTravelDTO){
        EmployeeTravel employeeTravel = employeeTravelsDAO.create();
        if (employeeTravelDTO.getTravelId() != null) employeeTravel.setTravel(travelsDAO.findOne(employeeTravelDTO.getTravelId()));
        if (employeeTravelDTO.getEmployeeId() != null) employeeTravel.setEmployee(employeesDAO.findOne(employeeTravelDTO.getEmployeeId()));
        if (employeeTravelDTO.getFlightId() != null) employeeTravel.setFlight(flightsDAO.findOne(employeeTravelDTO.getFlightId()));
        if (employeeTravelDTO.getCarRentId() != null) employeeTravel.setCarRent(carRentsDAO.findOne(employeeTravelDTO.getCarRentId()));
        if (employeeTravelDTO.getRoomId() != null) employeeTravel.setRoom(roomsDAO.findOne(employeeTravelDTO.getRoomId()));
        employeeTravelsDAO.persist(employeeTravel);
        return employeeTravel;
    }

    public EmployeeTravel update(Integer id, EmployeeTravelDTO employeeTravelDTO){
        EmployeeTravel employeeTravel = employeeTravelsDAO.findOne(id);
        if (employeeTravel == null){
            throw new IllegalArgumentException("employeeTravel id "
                    + id + " not found");
        }
        if (employeeTravelDTO.getTravelId() != null) employeeTravel.setTravel(travelsDAO.findOne(employeeTravelDTO.getTravelId()));
        if (employeeTravelDTO.getEmployeeId() != null) employeeTravel.setEmployee(employeesDAO.findOne(employeeTravelDTO.getEmployeeId()));
        if (employeeTravelDTO.getFlightId() != null) employeeTravel.setFlight(flightsDAO.findOne(employeeTravelDTO.getFlightId()));
        if (employeeTravelDTO.getCarRentId() != null) employeeTravel.setCarRent(carRentsDAO.findOne(employeeTravelDTO.getCarRentId()));
        if (employeeTravelDTO.getRoomId() != null) employeeTravel.setRoom(roomsDAO.findOne(employeeTravelDTO.getRoomId()));
        employeeTravelsDAO.update(employeeTravel);
        return employeeTravel;
    }

    public EmployeeTravel accept(Integer id){
        EmployeeTravel employeeTravel = employeeTravelsDAO.findOne(id);
        if (employeeTravel == null){
            throw new IllegalArgumentException("employeeTravel id "
                    + id + " not found");
        }
        employeeTravel.setStatus(true);
        employeeTravel.setRoom(accommodationService.bookAccommodation(employeeTravel));
        employeeTravelsDAO.update(employeeTravel);
        return employeeTravel;
    }

    public void changeAccommodation(Integer id) {
        EmployeeTravel employeeTravel = employeeTravelsDAO.findOne(id);
        List<Date> dates = calendarService.getDates(employeeTravel.getTravel().getStartDate(), employeeTravel.getTravel().getEndDate());
        Room room;
        if (employeeTravel.getRoom().getAccommodation().getAccommodationType() == "Apartments")
        {
            System.out.println("hello 1");
            room = accommodationService.bookRooms(accommodationsDAO.getHotel(employeeTravel.getRoom().getAccommodation().getOffice().getId()), dates);
        }
        else room = accommodationService.bookRooms(accommodationsDAO.getApartments(employeeTravel.getRoom().getAccommodation().getOffice().getId()), dates);
        employeeTravel.setRoom(room);
        employeeTravelsDAO.update(employeeTravel);
    }


    public void delete(Integer id){
        EmployeeTravel employeeTravel = employeeTravelsDAO.findOne(id);
        employeeTravelsDAO.delete(employeeTravel);
    }



}
