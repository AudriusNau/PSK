package rest;

import dto.CarRentDTO;
import entities.CarRent;
import interceptors.DevbridgeInterceptor;
import services.CarRentService;

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
@DevbridgeInterceptor
public class CarRentController {

    //NEED - 0 - nereikia, 1 - reikia, 2 - uzsakyta

    @Inject
    private CarRentService carRentService;

    @Path("/get/{carRentId}")
    @GET
    public CarRent find(@PathParam("carRentId") int id){
        return carRentService.getById(id);
    }

    @Path("/get/all")
    @GET
    public List<CarRent> find(){
        return carRentService.getAll();
    }

    @Path("/post")
    @POST
    @Transactional
    public CarRent create(CarRentDTO carRentDTO) {
        return carRentService.create(carRentDTO);
    }

    @Path("/put/{id}")
    @PUT @Transactional
    public Response update(@PathParam("id") int id,
                           CarRentDTO carRentDTO) {

        CarRent carRent = carRentService.update(id, carRentDTO);
        return Response.ok(carRent).build();
    }

    @Path("/delete/{id}")
    @DELETE @Transactional
    public Response delete(@PathParam("id") int id) {
        carRentService.delete(id);
        return Response.ok().build();
    }

}
