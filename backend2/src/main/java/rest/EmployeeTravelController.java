package rest;

import entities.CarRent;
import entities.EmployeeTravel;
import entities.Flight;
import entities.Room;
import lombok.Getter;
import lombok.Setter;
import persistence.*;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
@Path("/employeeTravel")
@Produces(MediaType.APPLICATION_JSON)
public class EmployeeTravelController {

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

    @Path("/get/{employeeTravelId}")
    @GET
    public EmployeeTravel findByEmployeeTravelId(@PathParam("employeeTravelId") Integer employeeTravelId){
        EmployeeTravel employeeTravel = employeeTravelsDAO.findOne(employeeTravelId);
        return employeeTravel;
    }

    @Path("/get/travelId/{travelId}")
    @GET
    public List <EmployeeTravel> findByTravelId(@PathParam("travelId") Integer travelId){
        List<EmployeeTravel> employeeTravels = employeeTravelsDAO.findByTravelId(travelId);
        return employeeTravels;
    }

    @Path("/get/employeeId/{employeeId}")
    @GET
    public List <EmployeeTravel> findByEmployee(@PathParam("employeeId") Integer employeeId){
        List<EmployeeTravel> employeeTravels = employeeTravelsDAO.findByEmployee(employeeId);
        return employeeTravels;
    }

    @Path("/get/flightId/{flightId}")
    @GET
    public List <EmployeeTravel> findByFlight(@PathParam("flightId") Integer flightId){
        List<EmployeeTravel> employeeTravels = employeeTravelsDAO.findByFlight(flightId);
        return employeeTravels;
    }

    @Path("/get/carRentId/{carRentId}")
    @GET
    public List <EmployeeTravel> findByCarRent(@PathParam("carRentId") Integer carRentId){
        List<EmployeeTravel> employeeTravels = employeeTravelsDAO.findByCarRent(carRentId);
        return employeeTravels;
    }

    @Path("/get/roomId/{roomId}")
    @GET
    public List <EmployeeTravel> findByRoom(@PathParam("roomId") Integer roomId){
        List<EmployeeTravel> employeeTravels = employeeTravelsDAO.findByRoom(roomId);
        return employeeTravels;
    }

    @Path("/get/getFlightByEmployeeTravelId/{employeeTravelId}")
    @GET
    public Flight findFlightByEmployeeTravelId(@PathParam("employeeTravelId") int id){
        Flight flight = (employeeTravelsDAO.findOne(id)).getFlight();
        return flight;
    }

    @Path("/get/getCarRentByEmployeeTravelId/{employeeTravelId}")
    @GET
    public CarRent findCarRentByEmployeeTravelId(@PathParam("employeeTravelId") int id){
        CarRent carRent = (employeeTravelsDAO.findOne(id)).getCarRent();
        return carRent;
    }

    @Path("/get/getRoomByEmployeeTravelId/{employeeTravelId}")
    @GET
    public Room findRoomByEmployeeTravelId(@PathParam("employeeTravelId") int id){
        Room room = (employeeTravelsDAO.findOne(id)).getRoom();
        return room;
    }

    @Path("/get/all")
    @GET
    public List<EmployeeTravel> find(){
        List<EmployeeTravel> employeeTravels = employeeTravelsDAO.loadAll();
        return employeeTravels;
    }

    @Path("/post")
    @POST
    @Transactional
    public EmployeeTravel create(@QueryParam("travelId") Integer travelId,
                                   @QueryParam("employeeId") Integer employeeId,
                                    @QueryParam("flightId") Integer flightId,
                                 @QueryParam("carRentId") Integer carRentId,
    @QueryParam("roomId") Integer roomId) {
        EmployeeTravel employeeTravel = new EmployeeTravel();
        if (travelId != null) employeeTravel.setTravel(travelsDAO.findOne(travelId));
        if (employeeId != null) employeeTravel.setEmployee(employeesDAO.findOne(employeeId));
        if (flightId != null) employeeTravel.setFlight(flightsDAO.findOne(flightId));
        if (carRentId != null) employeeTravel.setCarRent(carRentsDAO.findOne(carRentId));
        if (roomId != null) employeeTravel.setRoom(roomsDAO.findOne(roomId));
        System.out.println("employeeTravel set");
        employeeTravelsDAO.persist(employeeTravel);
        return employeeTravel;
    }

    @Path("/put/{id}")
    @PUT @Transactional
    public Response update(@PathParam("id") int id,
                           @QueryParam("travelId") Integer travelId,
                           @QueryParam("employeeId") Integer employeeId,
                           @QueryParam("flightId") Integer flightId,
                           @QueryParam("carRentId") Integer carRentId) {
        EmployeeTravel employeeTravel = employeeTravelsDAO.findOne(id);
        if (employeeTravel == null){
            throw new IllegalArgumentException("employeeTravel id "
                    + id + " not found");
        }
        if (employeeId != null) employeeTravel.setEmployee(employeesDAO.findOne(employeeId));
        if (travelId != null) employeeTravel.setTravel(travelsDAO.findOne(travelId));
        if (flightId != null) employeeTravel.setFlight(flightsDAO.findOne(flightId));
        if (carRentId != null) employeeTravel.setCarRent(carRentsDAO.findOne(carRentId));
        employeeTravelsDAO.update(employeeTravel);
        return Response.ok(employeeTravel).build();
    }

}
