package rest;

import dto.OfficeDTO;
import entities.Office;
import interceptors.DevbridgeInterceptor;
import lombok.Getter;
import lombok.Setter;
import persistence.OfficesDAO;

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
    @Setter
    @Getter
    private OfficesDAO officesDAO;

    @Path("/get/{officeId}")
    @GET
    public Office find(@PathParam("officeId") int id){
        Office office = officesDAO.findOne(id);
        return office;
    }

    @Path("/get/all")
    @GET
    public List<Office> find(){
        List<Office> offices = officesDAO.loadAll();
        return offices;
    }

    @Path("/post")
    @POST
    @Transactional
    public Office create(OfficeDTO officeDTO) {
        Office office = officesDAO.create();
        office.setName(officeDTO.getName());
        officesDAO.persist(office);
        return office;
    }

    @Path("/put/{id}")
    @PUT @Transactional
    public Response update(@PathParam("id") int id,
                           OfficeDTO officeDTO) {

        Office office = officesDAO.findOne(id);
        if (office == null){
            throw new IllegalArgumentException("office id "
                    + id + " not found");
        }
        if (officeDTO.getName() != null) office.setName(officeDTO.getName());
        officesDAO.update(office);
        return Response.ok(office).build();
    }

    @Path("/delete/{id}")
    @DELETE @Transactional
    public Response delete(@PathParam("id") int id) {
        Office office = officesDAO.findOne(id);
        officesDAO.delete(office);
        return Response.ok().build();
    }

}
