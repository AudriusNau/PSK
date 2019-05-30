package rest;

import dto.FlightDTO;
import entities.Flight;
import interceptors.DevbridgeInterceptor;
import lombok.Getter;
import lombok.Setter;
import persistence.FlightsDAO;
import services.FlightService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
@Path("/flight")
@Produces(MediaType.APPLICATION_JSON)
@DevbridgeInterceptor
public class FlightController {

    //NEED - 0 - nereikia, 1 - reikia, 2 - uzsakyta

    @Inject
    private FlightService flightService;

    @Path("/get/{flightId}")
    @GET
    public Flight find(@PathParam("flightId") int id){
        return flightService.getById(id);
    }

    @Path("/get/all")
    @GET
    public List<Flight> find(){
        return flightService.getAll();
    }

    @Path("/post")
    @POST
    @Transactional
    public Flight create(FlightDTO flightDTO) {
        return flightService.create(flightDTO);
    }

    @Path("/put/{id}")
    @PUT @Transactional
    public Response update(@PathParam("id") int id,
                           FlightDTO flightDTO) {

        Flight flight = flightService.update(id, flightDTO);
        return Response.ok(flight).build();
    }

    @Path("/delete/{id}")
    @DELETE @Transactional
    public Response delete(@PathParam("id") int id) {
        flightService.delete(id);
        return Response.ok().build();
    }

}
