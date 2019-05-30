package rest;

import dto.MergeTravelsDTO;
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
import services.TravelService;

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
    private TravelService travelService;

    @Path("/get/{travelId}")
    @GET
    public Travel find(@PathParam("travelId") int id){
        return travelService.getById(id);
    }

    @Path("/get/getByDepartureOfficeId/{departureOfficeId}")
    @GET
    public List<Travel> findByDepartureOfficeId(@PathParam("departureOfficeId") int departureOfficeId){
        return travelService.getByDepartureOffice(departureOfficeId);
    }

    @Path("/get/getByArrivalOfficeId/{arrivalOfficeId}")
    @GET
    public List<Travel> findByArrivalOfficeId(@PathParam("arrivalOfficeId") int arrivalOfficeId){
        return travelService.getByArrivalOffice(arrivalOfficeId);
    }

    @Path("/get/getByOrganiserId/{organiserId}")
    @GET
    public List<Travel> findByOrganiserId(@PathParam("organiserId") int organiserId){
        return travelService.getByOrganiserId(organiserId);
    }

    @Path("/get/getDepartureOfficeByTravelId/{travelId}")
    @GET
    public Office findDepartureOfficeByTravelId(@PathParam("travelId") int id){
        return travelService.getDepartureOffice(id);
    }

    @Path("/get/getArrivalOfficeByTravelId/{travelId}")
    @GET
    public Office findArrivalOfficeByTravelId(@PathParam("travelId") int id){
        return travelService.getArrivalOffice(id);
    }

    @Path("/get/getOrganiserByTravelId/{travelId}")
    @GET
    public Employee findOrganiserByTravelId(@PathParam("travelId") int id){
        return travelService.getOrganiser(id);
    }

    @Path("/get/all")
    @GET
    public List<Travel> find(){
        return travelService.getAll();
    }

    @Path("/post")
    @POST
    @Transactional
    public Travel create(
            TravelDTO travelDTO) {
        return travelService.create(travelDTO);
    }

    @Path("/put/{id}")
    @PUT @Transactional
    public Response update(@PathParam("id") int id,
                           TravelDTO travelDTO) {

        Travel travel = travelService.update(id, travelDTO);
        return Response.ok(travel).build();
    }

    @Path("/merge")
    @PUT @Transactional
    public Response mergeTravels(MergeTravelsDTO mergeTravelsDTO){
        Travel baseTravel = travelService.mergeTravels(mergeTravelsDTO);
        return Response.ok(baseTravel).build();
    }

    @Path("/delete/{id}")
    @DELETE @Transactional
    public Response delete(@PathParam("id") int id) {
        travelService.delete(id);
        return Response.ok().build();
    }

}
