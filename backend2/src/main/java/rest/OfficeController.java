package rest;

import entities.Office;
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
    public Office create(@QueryParam("name") String name) {
        System.out.println("office post");
        Office office = new Office();
        office.setName(name);
        //office.setOffice();
        officesDAO.persist(office);
        return office;
    }

    @Path("/put/{id}")
    @PUT @Transactional
    public Response update(@PathParam("id") int id,
                           @QueryParam("name") String name) {

        Office office = officesDAO.findOne(id);
        if (office == null){
            throw new IllegalArgumentException("office id "
                    + id + " not found");
        }
        if (name != null) office.setName(name);
        officesDAO.update(office);
        return Response.ok(office).build();
    }

}