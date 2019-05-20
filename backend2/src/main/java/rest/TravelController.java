package rest;

import entities.Travel;
import entities.Office;
import lombok.Getter;
import lombok.Setter;
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
public class TravelController {

    @Inject
    @Setter
    @Getter
    private TravelsDAO travelsDAO;

    @Inject
    @Setter
    @Getter
    private OfficesDAO officesDAO;

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
                         @QueryParam("date") String date,
                         @QueryParam("departureOfficeId") Integer departureOfficeId,
                                @QueryParam("arrivalOfficeId") Integer arrivalOfficeId,
                         @QueryParam("price") Double price) {
        System.out.println("travel post");
        Travel travel = new Travel();
        travel.setDate(date);
        travel.setDepartureOffice(officesDAO.findOne(departureOfficeId));
        travel.setArrivalOffice(officesDAO.findOne(arrivalOfficeId));
        travel.setPrice(price);
        travelsDAO.persist(travel);
        return travel;
    }

    @Path("/put/{id}")
    @PUT @Transactional
    public Response update(@PathParam("id") int id,
                           @QueryParam("date") String date,
                           @QueryParam("departureOffice") Integer departureOfficeId,
                           @QueryParam("arrivalOfficeId") Integer arrivalOfficeId,
                           @QueryParam("price") Double price) {

        Travel travel = travelsDAO.findOne(id);
        if (travel == null){
            throw new IllegalArgumentException("travel id "
                    + id + " not found");
        }
        if (date != null) travel.setDate(date);
        if (departureOfficeId != null) travel.setDepartureOffice(officesDAO.findOne(departureOfficeId));
        if (arrivalOfficeId != null) travel.setArrivalOffice(officesDAO.findOne(arrivalOfficeId));
        travelsDAO.update(travel);
        return Response.ok(travel).build();
    }

}
