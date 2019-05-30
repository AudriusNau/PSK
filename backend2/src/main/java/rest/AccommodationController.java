package rest;

import dto.AccommodationDTO;
import entities.Accommodation;
import entities.Office;
import interceptors.DevbridgeInterceptor;
import services.AccommodationService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
@Path("/accommodation")
@Produces(MediaType.APPLICATION_JSON)
@DevbridgeInterceptor
public class AccommodationController {

    @Inject
    private AccommodationService accommodationService;

    @Path("/get/{accommodationId}")
    @GET
    public Accommodation find(@PathParam("accommodationId") int id){
        return accommodationService.getById(id);
    }

    @Path("/get/getByOfficeId/{officeId}")
    @GET
    public List<Accommodation> findByOfficeId(@PathParam("officeId") int officeId){
        return accommodationService.getByOfficeId(officeId);
    }

    @Path("/get/getOfficeByAccommodationId/{accommodationId}")
    @GET
    public Office findOfficeByAccommodationId(@PathParam("accommodationId") int id){
        return accommodationService.getOfficeByAccommodationId(id);
    }

    @Path("/get/all")
    @GET
    public List<Accommodation> find(){
        return accommodationService.getAll();
    }

    @Path("/post")
    @POST
    @Transactional
    public Accommodation create(AccommodationDTO accommodationDTO) {
        return accommodationService.create(accommodationDTO);
    }

    @Path("/put/{id}")
    @PUT @Transactional
    public Response update(@PathParam("id") int id,
                           AccommodationDTO accommodationDTO) {

        Accommodation accommodation = accommodationService.update(id, accommodationDTO);
        return Response.ok(accommodation).build();
    }

    @Path("/delete/{id}")
    @DELETE @Transactional
    public Response delete(@PathParam("id") int id) {
        accommodationService.delete(id);
        return Response.ok().build();
    }

}
