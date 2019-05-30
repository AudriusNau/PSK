package rest;

import dto.CalendarDTO;
import entities.Calendar;
import interceptors.DevbridgeInterceptor;
import lombok.Getter;
import lombok.Setter;
import persistence.CalendarsDAO;
import services.CalendarService;

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
@DevbridgeInterceptor
public class CalendarController {

    @Inject
    private CalendarService calendarService;

    @Path("/get/{date}")
    @GET
    public Calendar find(@PathParam("date") String date){
        return calendarService.getByDate(date);
    }

    @Path("/get/all")
    @GET
    public List<Calendar> find(){
        return calendarService.getAll();
    }

    @Path("/post/{date}")
    @POST
    @Transactional
    public Calendar create(CalendarDTO calendarDTO) {
        return calendarService.create(calendarDTO);
    }

    @Path("/delete/{date}")
    @DELETE @Transactional
    public Response delete(@PathParam("date") String date) {
        calendarService.delete(date);
        return Response.ok().build();
    }

}
