package rest;

import entities.Room;
import entities.Accommodation;
import lombok.Getter;
import lombok.Setter;
import persistence.AccommodationsDAO;
import persistence.RoomsDAO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
@Path("/room")
@Produces(MediaType.APPLICATION_JSON)
public class RoomController {

    @Inject
    @Setter
    @Getter
    private RoomsDAO roomsDAO;

    @Inject
    @Setter
    @Getter
    private AccommodationsDAO accommodationsDAO;

    @Path("/get/{roomId}")
    @GET
    public Room find(@PathParam("roomId") int id){
        Room room = roomsDAO.findOne(id);
        return room;
    }

    @Path("/get/getAccommodationByRoomId/{roomId}")
    @GET
    public Accommodation findAccommodationByRoomId(@PathParam("roomId") int id){
        Accommodation accommodation = (roomsDAO.findOne(id)).getAccommodation();
        return accommodation;
    }

    @Path("/get/getByAccommodationId/{accommodationId}")
    @GET
    public List<Room> findByAccommodationId(@PathParam("accommodationId") int accommodationId){
        List<Room> rooms = roomsDAO.findByAccommodationId(accommodationId);
        return rooms;
    }

    @Path("/get/all")
    @GET
    public List<Room> find(){
        List<Room> rooms = roomsDAO.loadAll();
        return rooms;
    }

    @Path("/post")
    @POST
    @Transactional
    public Room create(@QueryParam("roomNumber") Integer roomNumber,
    @QueryParam("accommodationId") Integer accommodationId) {
        System.out.println("room post");
        Room room = new Room();
        room.setRoomNumber(roomNumber);
        room.setAccommodation(accommodationsDAO.findOne(accommodationId));
        roomsDAO.persist(room);
        return room;
    }

    @Path("/put/{id}")
    @PUT @Transactional
    public Response update(@PathParam("id") int id,
                           @QueryParam("roomNumber") Integer roomNumber,
                           @QueryParam("accommodationId") Integer accommodationId) {

        Room room = roomsDAO.findOne(id);
        if (room == null){
            throw new IllegalArgumentException("room id "
                    + id + " not found");
        }
        if (roomNumber != null) room.setRoomNumber(roomNumber);
        if (accommodationId != null) room.setAccommodation(accommodationsDAO.findOne(accommodationId));
        roomsDAO.update(room);
        return Response.ok(room).build();
    }

    @Path("/delete/{id}")
    @DELETE @Transactional
    public Response delete(@PathParam("id") int id) {
        Room room = roomsDAO.findOne(id);
        roomsDAO.delete(room);
        return Response.ok().build();
    }

}
