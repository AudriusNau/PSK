package rest;

import dto.OfficeDTO;
import entities.Office;
import interceptors.DevbridgeInterceptor;
import lombok.Getter;
import lombok.Setter;
import persistence.OfficesDAO;
import services.OfficeService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
@Path("/office")
@Produces(MediaType.APPLICATION_JSON)
@DevbridgeInterceptor
public class OfficeController {

    @Inject
    private OfficeService officeService;


    @Path("/get/{officeId}")
    @GET
    public Office find(@PathParam("officeId") int id){
        return officeService.getById(id);
    }

    @Path("/get/all")
    @GET
    public List<Office> find(){
        return officeService.getAll();
    }

    @Path("/post")
    @POST
    @Transactional
    public Office create(OfficeDTO officeDTO) {
        return officeService.create(officeDTO);
    }

    @Path("/put/{id}")
    @PUT @Transactional
    public Response update(@PathParam("id") int id,
                           OfficeDTO officeDTO) {

        Office office = officeService.update(id, officeDTO);
        return Response.ok(office).build();
    }

    @Path("/delete/{id}")
    @DELETE @Transactional
    public Response delete(@PathParam("id") int id) {
        officeService.delete(id);
        return Response.ok().build();
    }

}
