package rest;

import dto.RoomDTO;
import entities.Room;
import entities.Accommodation;
import interceptors.DevbridgeInterceptor;
import services.RoomService;

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
@DevbridgeInterceptor
public class RoomController {

    @Inject
    private RoomService roomService;

    @Path("/get/{roomId}")
    @GET
    public Room find(@PathParam("roomId") int id){
        return roomService.getById(id);
    }

    @Path("/get/getAccommodationByRoomId/{roomId}")
    @GET
    public Accommodation findAccommodationByRoomId(@PathParam("roomId") int id){
        return roomService.getAccommodation(id);
    }

    @Path("/get/getByAccommodationId/{accommodationId}")
    @GET
    public List<Room> findByAccommodationId(@PathParam("accommodationId") int accommodationId){
        return roomService.getByAccommodationId(accommodationId);
    }

    @Path("/get/all")
    @GET
    public List<Room> find(){
        return roomService.getAll();
    }

    @Path("/post")
    @POST
    @Transactional
    public Room create(RoomDTO roomDTO) {
        return roomService.create(roomDTO);
    }

    @Path("/put/{id}")
    @PUT @Transactional
    public Response update(@PathParam("id") int id,
                           RoomDTO roomDTO) {

        Room room = roomService.update(id, roomDTO);
        return Response.ok(room).build();
    }

    @Path("/delete/{id}")
    @DELETE @Transactional
    public Response delete(@PathParam("id") int id) {
        roomService.delete(id);
        return Response.ok().build();
    }

}
