package rest;

import entities.Flight;
import interceptors.DevbridgeInterceptor;
import lombok.Getter;
import lombok.Setter;
import persistence.FlightsDAO;

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
    @Setter
    @Getter
    private FlightsDAO flightsDAO;

    @Path("/get/{flightId}")
    @GET
    public Flight find(@PathParam("flightId") int id){
        Flight flight = flightsDAO.findOne(id);
        return flight;
    }

    @Path("/get/all")
    @GET
    public List<Flight> find(){
        List<Flight> flights = flightsDAO.loadAll();
        return flights;
    }

    @Path("/post")
    @POST
    @Transactional
    public Flight create(@QueryParam("need") Integer need,
                          @QueryParam("info") String info) {
        System.out.println("flight post");
        Flight flight = new Flight();
        flight.setNeed(need);
        flight.setInfo(info);
        flightsDAO.persist(flight);
        return flight;
    }

    @Path("/put/{id}")
    @PUT @Transactional
    public Response update(@PathParam("id") int id,
                           @QueryParam("need") Integer need,
                           @QueryParam("info") String info) {

        Flight flight = flightsDAO.findOne(id);
        if (flight == null){
            throw new IllegalArgumentException("flight id "
                    + id + " not found");
        }
        if (need != null) flight.setNeed(need);
        if (info != null) flight.setInfo(info);
        flightsDAO.update(flight);
        return Response.ok(flight).build();
    }

    @Path("/delete/{id}")
    @DELETE @Transactional
    public Response delete(@PathParam("id") int id) {
        Flight flight = flightsDAO.findOne(id);
        flightsDAO.delete(flight);
        return Response.ok().build();
    }

}
