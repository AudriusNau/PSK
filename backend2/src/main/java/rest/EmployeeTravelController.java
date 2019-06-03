package rest;

import dto.EmployeeTravelDTO;
import dto.MergeTravelsDTO;
import entities.*;
import interceptors.DevbridgeInterceptor;
import lombok.Getter;
import lombok.Setter;
import persistence.*;
import services.EmployeeTravelService;

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
@DevbridgeInterceptor
public class EmployeeTravelController {

    @Inject
    private EmployeeTravelService employeeTravelService;

    @Path("/get/{employeeTravelId}")
    @GET
    public EmployeeTravel findByEmployeeTravelId(@PathParam("employeeTravelId") Integer employeeTravelId){
        return employeeTravelService.getByEmployeeTravelId(employeeTravelId);
    }

    @Path("/get/travelId/{travelId}")
    @GET
    public List <EmployeeTravel> findByTravelId(@PathParam("travelId") Integer travelId){
        return employeeTravelService.getByTravelId(travelId);
    }

    @Path("/get/employeeId/{employeeId}")
    @GET
    public List <EmployeeTravel> findByEmployee(@PathParam("employeeId") Integer employeeId){
        return employeeTravelService.getByEmployeeId(employeeId);
    }

    @Path("/get/getPendingTravelsForEmployee/{employeeId}")
    @GET
    public List <EmployeeTravel> findPendingTravelsForEmployee(@PathParam("employeeId") Integer employeeId){
        return employeeTravelService.getPendingTravelsForEmployee(employeeId);
    }

    @Path("/get/getAcceptedTravelsForEmployee/{employeeId}")
    @GET
    public List <EmployeeTravel> findAcceptedTravelsForEmployee(@PathParam("employeeId") Integer employeeId){
        return employeeTravelService.getAcceptedTravelsForEmployee(employeeId);
    }

    @Path("/get/flightId/{flightId}")
    @GET
    public List <EmployeeTravel> findByFlight(@PathParam("flightId") Integer flightId){
        return employeeTravelService.getByFlight(flightId);
    }

    @Path("/get/carRentId/{carRentId}")
    @GET
    public List <EmployeeTravel> findByCarRent(@PathParam("carRentId") Integer carRentId){
        return employeeTravelService.getByCarRent(carRentId);
    }

    @Path("/get/roomId/{roomId}")
    @GET
    public List <EmployeeTravel> findByRoom(@PathParam("roomId") Integer roomId){
        return employeeTravelService.getByRoom(roomId);
    }

    @Path("/get/getFlightByEmployeeTravelId/{employeeTravelId}")
    @GET
    public Flight findFlightByEmployeeTravelId(@PathParam("employeeTravelId") int id){
        return employeeTravelService.getFlight(id);
    }

    @Path("/get/getCarRentByEmployeeTravelId/{employeeTravelId}")
    @GET
    public CarRent findCarRentByEmployeeTravelId(@PathParam("employeeTravelId") int id){
        return employeeTravelService.getCarRent(id);
    }

    @Path("/get/getRoomByEmployeeTravelId/{employeeTravelId}")
    @GET
    public Room findRoomByEmployeeTravelId(@PathParam("employeeTravelId") int id){
        return employeeTravelService.getRoom(id);
    }

    @Path("/get/all")
    @GET
    public List<EmployeeTravel> find(){
        return employeeTravelService.getAll();
    }

    @Path("/post")
    @POST
    @Transactional
    public EmployeeTravel create(EmployeeTravelDTO employeeTravelDTO) {
        return employeeTravelService.create(employeeTravelDTO);
    }

    @Path("/put/{id}")
    @PUT @Transactional
    public Response update(@PathParam("id") int id,
                           EmployeeTravelDTO employeeTravelDTO) {
        EmployeeTravel employeeTravel = employeeTravelService.update(id, employeeTravelDTO);
        return Response.ok(employeeTravel).build();
    }

    @Path("/changeAccommodation/{id}")
    @PUT @Transactional
    public Response changeAccommodation(@PathParam("id") int id)
    {
        employeeTravelService.changeAccommodation(id);
        return Response.ok(employeeTravelService.getByEmployeeTravelId(id)).build();
    }

    @Path("/accept/{id}")
    @PUT @Transactional
    public Response accept(@PathParam("id") int id) {
        EmployeeTravel employeeTravel = employeeTravelService.accept(id);
        return Response.ok(employeeTravel).build();
    }

    @Path("/delete/{id}")
    @DELETE @Transactional
    public Response delete(@PathParam("id") int id) {
        employeeTravelService.delete(id);
        return Response.ok().build();
    }

}
