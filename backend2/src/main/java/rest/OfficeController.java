package rest;

import dto.OfficeDTO;
import dto.RoomAvailabilityDTO;
import entities.Accommodation;
import entities.Office;
import entities.Room;
import interceptors.DevbridgeInterceptor;
import lombok.Getter;
import lombok.Setter;
import persistence.OfficesDAO;
import services.AccommodationService;
import services.OfficeService;
import services.RoomService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
@Path("/office")
@Produces(MediaType.APPLICATION_JSON)
@DevbridgeInterceptor
public class OfficeController {

    @Inject
    private OfficeService officeService;

    @Inject
    private AccommodationService accommodationService;

    @Inject
    private RoomService roomService;


    @Path("/get/{officeId}")
    @GET
    public Office find(@PathParam("officeId") int id){
        return officeService.getById(id);
    }

    @Path("/get/all")
    @GET
    public List<Office> find(){
        return officeService.getAll();
    }

    /*@Path("/get/getFreeRooms")
    @GET
    public ArrayList<Room> findFreeRooms(RoomAvailabilityDTO roomAvailabilityDTO) {
        List<Accommodation> apartments = accommodationService.getApartments(roomAvailabilityDTO.getOfficeId());
        ArrayList<Room> availableRooms = roomService.getAvailableRooms(apartments, roomAvailabilityDTO.getStartDate(), roomAvailabilityDTO.getEndDate());
        return availableRooms;
    }*/

    @Path("/post")
    @POST
    @Transactional
    public Office create(OfficeDTO officeDTO) {
        return officeService.create(officeDTO);
    }

    @Path("/put/{id}")
    @PUT @Transactional
    public Response update(@PathParam("id") int id,
                           OfficeDTO officeDTO) {

        Office office = officeService.update(id, officeDTO);
        return Response.ok(office).build();
    }

    @Path("/delete/{id}")
    @DELETE @Transactional
    public Response delete(@PathParam("id") int id) {
        officeService.delete(id);
        return Response.ok().build();
    }

}
