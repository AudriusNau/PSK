package rest;

import dto.CarRentDTO;
import entities.CarRent;
import interceptors.DevbridgeInterceptor;
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
@DevbridgeInterceptor
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
    public CarRent create(CarRentDTO carRentDTO) {
        CarRent carRent = new CarRent();
        carRent.setNeed(carRentDTO.getNeed());
        carRent.setInfo(carRentDTO.getInfo());
        carRentsDAO.persist(carRent);
        return carRent;
    }

    @Path("/put/{id}")
    @PUT @Transactional
    public Response update(@PathParam("id") int id,
                           CarRentDTO carRentDTO) {

        CarRent carRent = carRentsDAO.findOne(id);
        if (carRent == null){
            throw new IllegalArgumentException("carRent id "
                    + id + " not found");
        }
        if (carRentDTO.getNeed() != null) carRent.setNeed(carRentDTO.getNeed());
        if (carRentDTO.getInfo() != null) carRent.setInfo(carRentDTO.getInfo());
        carRentsDAO.update(carRent);
        return Response.ok(carRent).build();
    }

    @Path("/delete/{id}")
    @DELETE @Transactional
    public Response delete(@PathParam("id") int id) {
        CarRent carRent = carRentsDAO.findOne(id);
        carRentsDAO.delete(carRent);
        return Response.ok().build();
    }

}
