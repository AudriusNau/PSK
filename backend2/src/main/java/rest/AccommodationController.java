package rest;

import dto.AccommodationDTO;
import entities.Accommodation;
import entities.Office;
import interceptors.DevbridgeInterceptor;
import lombok.Getter;
import lombok.Setter;
import persistence.AccommodationsDAO;
import persistence.OfficesDAO;

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
    @Setter
    @Getter
    private AccommodationsDAO accommodationsDAO;

    @Inject
    @Setter
    @Getter
    private OfficesDAO officesDAO;

    @Path("/get/{accommodationId}")
    @GET
    public Accommodation find(@PathParam("accommodationId") int id){
        Accommodation accommodation = accommodationsDAO.findOne(id);
        return accommodation;
    }

    @Path("/get/getByOfficeId/{officeId}")
    @GET
    public List<Accommodation> findByOfficeId(@PathParam("officeId") int officeId){
        List<Accommodation> accommodations = accommodationsDAO.findByOfficeId(officeId);
        return accommodations;
    }

    @Path("/get/getOfficeByAccommodationId/{accommodationId}")
    @GET
    public Office findOfficeByAccommodationId(@PathParam("accommodationId") int id){
        Office office = (accommodationsDAO.findOne(id)).getOffice();
        return office;
    }

    @Path("/get/all")
    @GET
    public List<Accommodation> find(){
        List<Accommodation> accommodations = accommodationsDAO.loadAll();
        return accommodations;
    }

    @Path("/post")
    @POST
    @Transactional
    public Accommodation create(AccommodationDTO accommodationDTO) {
        Accommodation accommodation = accommodationsDAO.create();
        accommodation.setName(accommodationDTO.getName());
        accommodation.setOffice(officesDAO.findOne(accommodationDTO.getOfficeId()));
        accommodation.setAccommodationType(accommodationDTO.getAccommodationType());
        accommodationsDAO.persist(accommodation);
        return accommodation;
    }

    @Path("/put/{id}")
    @PUT @Transactional
    public Response update(@PathParam("id") int id,
                           AccommodationDTO accommodationDTO) {

        Accommodation accommodation = accommodationsDAO.findOne(id);
        if (accommodation == null){
            throw new IllegalArgumentException("accommodation id "
                    + id + " not found");
        }
        if (accommodationDTO.getName() != null) accommodation.setName(accommodationDTO.getName());
        if (accommodationDTO.getOfficeId() != null) accommodation.setOffice(officesDAO.findOne(accommodationDTO.getOfficeId()));
        if (accommodationDTO.getAccommodationType() != null) accommodation.setAccommodationType(accommodationDTO.getAccommodationType());
        accommodationsDAO.update(accommodation);
        return Response.ok(accommodation).build();
    }

    @Path("/delete/{id}")
    @DELETE @Transactional
    public Response delete(@PathParam("id") int id) {
        Accommodation accommodation = accommodationsDAO.findOne(id);
        accommodationsDAO.delete(accommodation);
        return Response.ok().build();
    }

}
