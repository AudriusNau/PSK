package rest;

import entities.CarRent;
import lombok.Getter;
import lombok.Setter;
import persistence.CarRentsDAO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
@Path("/carRent")
@Produces(MediaType.APPLICATION_JSON)
public class CarRentController {

    //NEED - 0 - nereikia, 1 - reikia, 2 - uzsakyta

    @Inject
    @Setter
    @Getter
    private CarRentsDAO carRentsDAO;

    @Path("/get/{carRentId}")
    @GET
    public CarRent find(@PathParam("carRentId") int id){
        CarRent carRent = carRentsDAO.findOne(id);
        return carRent;
    }

    @Path("/get/all")
    @GET
    public List<CarRent> find(){
        List<CarRent> carRents = carRentsDAO.loadAll();
        return carRents;
    }

    @Path("/post")
    @POST
    @Transactional
    public CarRent create(@QueryParam("need") Integer need,
                           @QueryParam("info") String info) {
        System.out.println("carRent post");
        CarRent carRent = new CarRent();
        carRent.setNeed(need);
        carRent.setInfo(info);
        carRentsDAO.persist(carRent);
        return carRent;
    }

    @Path("/put/{id}")
    @PUT @Transactional
    public Response update(@PathParam("id") int id,
                           @QueryParam("need") Integer need,
                           @QueryParam("info") String info) {

        CarRent carRent = carRentsDAO.findOne(id);
        if (carRent == null){
            throw new IllegalArgumentException("carRent id "
                    + id + " not found");
        }
        if (need != null) carRent.setNeed(need);
        if (info != null) carRent.setInfo(info);
        carRentsDAO.update(carRent);
        return Response.ok(carRent).build();
    }

}
