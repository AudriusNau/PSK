package rest;

import dto.TravelDTO;
import entities.Employee;
import entities.Travel;
import entities.Office;
import interceptors.DevbridgeInterceptor;
import lombok.Getter;
import lombok.Setter;
import persistence.EmployeesDAO;
import persistence.TravelsDAO;
import persistence.OfficesDAO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
@Path("/travel")
@Produces(MediaType.APPLICATION_JSON)
@DevbridgeInterceptor
public class TravelController {

    @Inject
    @Setter
    @Getter
    private TravelsDAO travelsDAO;

    @Inject
    @Setter
    @Getter
    private OfficesDAO officesDAO;

    @Inject
    @Setter
    @Getter
    private EmployeesDAO employeesDAO;

    @Path("/get/{travelId}")
    @GET
    public Travel find(@PathParam("travelId") int id){
        Travel travel = travelsDAO.findOne(id);
        return travel;
    }

    @Path("/get/getByDepartureOfficeId/{departureOfficeId}")
    @GET
    public List<Travel> findByDepartureOfficeId(@PathParam("departureOfficeId") int departureOfficeId){
        List<Travel> travels = travelsDAO.findByDepartureOfficeId(departureOfficeId);
        return travels;
    }

    @Path("/get/getByArrivalOfficeId/{arrivalOfficeId}")
    @GET
    public List<Travel> findByArrivalOfficeId(@PathParam("arrivalOfficeId") int arrivalOfficeId){
        List<Travel> travels = travelsDAO.findByArrivalOfficeId(arrivalOfficeId);
        return travels;
    }

    @Path("/get/getByOrganiserId/{organiserId}")
    @GET
    public List<Travel> findByOrganiserId(@PathParam("organiserId") int organiserId){
        List<Travel> travels = travelsDAO.findByOrganiserId(organiserId);
        return travels;
    }

    @Path("/get/getDepartureOfficeByTravelId/{travelId}")
    @GET
    public Office findDepartureOfficeByTravelId(@PathParam("travelId") int id){
        Office office = (travelsDAO.findOne(id)).getDepartureOffice();
        return office;
    }

    @Path("/get/getArrivalOfficeByTravelId/{travelId}")
    @GET
    public Office findArrivalOfficeByTravelId(@PathParam("travelId") int id){
        Office office = (travelsDAO.findOne(id)).getArrivalOffice();
        return office;
    }

    @Path("/get/getOrganiserByTravelId/{travelId}")
    @GET
    public Employee findOrganiserByTravelId(@PathParam("travelId") int id){
        Employee employee = (travelsDAO.findOne(id)).getOrganiser();
        return employee;
    }

    @Path("/get/all")
    @GET
    public List<Travel> find(){
        List<Travel> travels = travelsDAO.loadAll();
        return travels;
    }

    @Path("/post")
    @POST
    @Transactional
    public Travel create(
            TravelDTO travelDTO) {
        Travel travel = travelsDAO.create();
        travel.setDate(travelDTO.getDate());
        travel.setDepartureOffice(officesDAO.findOne(travelDTO.getDepartureOfficeId()));
        travel.setArrivalOffice(officesDAO.findOne(travelDTO.getArrivalOfficeId()));
        travel.setPrice(travelDTO.getPrice());
        travel.setOrganiser(employeesDAO.findOne(travelDTO.getOrganiserId()));
        travelsDAO.persist(travel);
        return travel;
    }

    @Path("/put/{id}")
    @PUT @Transactional
    public Response update(@PathParam("id") int id,
                           TravelDTO travelDTO) {

        Travel travel = travelsDAO.findOne(id);
        if (travel == null){
            throw new IllegalArgumentException("travel id "
                    + id + " not found");
        }
        if (travelDTO.getDate() != null) travel.setDate(travelDTO.getDate());
        if (travelDTO.getDepartureOfficeId() != null) travel.setDepartureOffice(officesDAO.findOne(travelDTO.getDepartureOfficeId()));
        if (travelDTO.getArrivalOfficeId() != null) travel.setArrivalOffice(officesDAO.findOne(travelDTO.getArrivalOfficeId()));
        if (travelDTO.getOrganiserId() != null) travel.setOrganiser(employeesDAO.findOne(travelDTO.getOrganiserId()));
        if (travelDTO.getPrice() != null) travel.setPrice(travelDTO.getPrice());
        travelsDAO.update(travel);
        return Response.ok(travel).build();
    }

    @Path("/delete/{id}")
    @DELETE @Transactional
    public Response delete(@PathParam("id") int id) {
        Travel travel = travelsDAO.findOne(id);
        travelsDAO.delete(travel);
        return Response.ok().build();
    }

}
