package rest;

import dto.RoomCalendarDTO;
import entities.Calendar;
import entities.RoomCalendar;
import interceptors.DevbridgeInterceptor;
import lombok.Getter;
import lombok.Setter;
import persistence.RoomCalendarsDAO;
import persistence.RoomsDAO;
import persistence.CalendarsDAO;
import services.RoomCalendarService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
@Path("/roomCalendar")
@Produces(MediaType.APPLICATION_JSON)
@DevbridgeInterceptor
public class RoomCalendarController {

    @Inject
    private RoomCalendarService roomCalendarService;

    @Path("/get/date/{date}")
    @GET
    public List <RoomCalendar> findByDate(@PathParam("date") String date){
        return roomCalendarService.getByDate(date);
    }

    @Path("/get/room/{roomId}")
    @GET
    public List <RoomCalendar> findByRoom(@PathParam("roomId") Integer roomId){
        return roomCalendarService.getByRoom(roomId);
    }

    @Path("/get/all")
    @GET
    public List<RoomCalendar> find(){
        return roomCalendarService.getAll();
    }

    @Path("/post")
    @POST
    @Transactional
    public RoomCalendar create(RoomCalendarDTO roomCalendarDTO) {
        return roomCalendarService.create(roomCalendarDTO);
    }

    @Path("/delete/{id}")
    @DELETE @Transactional
    public Response delete(@PathParam("id") int id) {
        roomCalendarService.delete(id);
        return Response.ok().build();
    }

}
