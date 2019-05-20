package rest;

import entities.Calendar;
import lombok.Getter;
import lombok.Setter;
import persistence.CalendarsDAO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
@Path("/calendar")
@Produces(MediaType.APPLICATION_JSON)
public class CalendarController {

    @Inject
    @Setter
    @Getter
    private CalendarsDAO calendarsDAO;

    @Path("/get/{date}")
    @GET
    public Calendar find(@PathParam("date") String date){
        Calendar calendar = calendarsDAO.findOne(date);
        return calendar;
    }

    @Path("/get/all")
    @GET
    public List<Calendar> find(){
        List<Calendar> calendars = calendarsDAO.loadAll();
        return calendars;
    }

    @Path("/post/{date}")
    @POST
    @Transactional
    public Calendar create(@PathParam("date") String date) {
        System.out.println("calendar post");
        Calendar calendar = new Calendar();
        calendar.setDate(date);
        calendarsDAO.persist(calendar);
        return calendar;
    }

    /*@Path("/put/{date}")
    @PUT @Transactional
    public Response update(@PathParam("date") String date,
                           @QueryParam("first_name") String firstName,
                           @QueryParam("last_name") String lastName,
                           @QueryParam("role") String role) {

        Calendar calendar = calendarsDAO.findOne(date);
        if (calendar == null){
            throw new IllegalArgumentException("calendar date "
                    + date + " not found");
        }
        calendar.setFirstName(firstName);
        calendar.setLastName(lastName);
        calendar.setRole(role);
        calendarsDAO.update(calendar);
        return Response.ok(calendar).build();
    }*/

}
