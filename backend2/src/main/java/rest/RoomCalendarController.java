package rest;

import dto.RoomCalendarDTO;
import entities.RoomCalendar;
import interceptors.DevbridgeInterceptor;
import lombok.Getter;
import lombok.Setter;
import persistence.RoomCalendarsDAO;
import persistence.RoomsDAO;
import persistence.CalendarsDAO;

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
    @Setter
    @Getter
    private RoomCalendarsDAO roomCalendarsDAO;

    @Inject
    @Setter
    @Getter
    private RoomsDAO roomsDAO;

    @Inject
    @Setter
    @Getter
    private CalendarsDAO calendarsDAO;

    /*@Path("/get/date/{date}")
    @GET
    public RoomCalendar findByDate(@PathParam("date") String date){
        RoomCalendar roomCalendar = roomCalendarsDAO.findByDate(date);
        System.out.println(roomCalendar.getCalendar());
        return roomCalendar;//pakeiciam i lista
    }*/

    @Path("/get/date/{date}")
    @GET
    public List <RoomCalendar> findByDate(@PathParam("date") String date){
        List<RoomCalendar> roomCalendars = roomCalendarsDAO.findByDate(date);
        return roomCalendars;
    }

    @Path("/get/room/{roomId}")
    @GET
    public List <RoomCalendar> findByDate(@PathParam("roomId") Integer roomId){
        List<RoomCalendar> roomCalendars = roomCalendarsDAO.findByRoom(roomId);
        return roomCalendars;
    }

    @Path("/get/all")
    @GET
    public List<RoomCalendar> find(){
        List<RoomCalendar> roomCalendars = roomCalendarsDAO.loadAll();
        return roomCalendars;
    }

    @Path("/post")
    @POST
    @Transactional
    public RoomCalendar create(RoomCalendarDTO roomCalendarDTO) {
        System.out.println("roomCalendar post");
        RoomCalendar roomCalendar = new RoomCalendar();
        /*roomCalendar.setDate(date);
        roomCalendar.setRoomId(roomId);*/
        roomCalendar.setCalendar(calendarsDAO.findOne(roomCalendarDTO.getDate()));
        roomCalendar.setRoom(roomsDAO.findOne(roomCalendarDTO.getRoomId()));
        System.out.println("roomCalendar set");
        roomCalendarsDAO.persist(roomCalendar);
        return roomCalendar;
    }

    @Path("/delete/{id}")
    @DELETE @Transactional
    public Response delete(@PathParam("id") int id) {
        RoomCalendar roomCalendar = roomCalendarsDAO.findOne(id);
        roomCalendarsDAO.delete(roomCalendar);
        return Response.ok().build();
    }

}
