package services;

import dto.RoomDTO;
import entities.Accommodation;
import entities.Room;
import interceptors.DevbridgeInterceptor;
import lombok.Getter;
import lombok.Setter;
import persistence.AccommodationsDAO;
import persistence.RoomsDAO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
@DevbridgeInterceptor
public class RoomService {

    @Inject
    @Setter
    @Getter
    private RoomsDAO roomsDAO;

    @Inject
    @Setter
    @Getter
    private AccommodationsDAO accommodationsDAO;

    public List<Room> getAll(){
        return roomsDAO.loadAll();
    }

    public List<Room> getByAccommodationId(Integer id){
        return roomsDAO.findByAccommodationId(id);
    }

    public Room getById(Integer id){
        return roomsDAO.findOne(id);
    }

    public Accommodation getAccommodation(Integer id){
        return (roomsDAO.findOne(id)).getAccommodation();
    }

    public Room create(RoomDTO roomDTO){
        Room room = roomsDAO.create();
        room.setRoomNumber(roomDTO.getRoomNumber());
        room.setAccommodation(accommodationsDAO.findOne(roomDTO.getAccommodationId()));
        roomsDAO.persist(room);
        return room;
    }

    public Room update(Integer id, RoomDTO roomDTO){
        Room room = roomsDAO.findOne(id);
        if (room == null){
            throw new IllegalArgumentException("room id "
                    + id + " not found");
        }
        if (roomDTO.getRoomNumber() != null) room.setRoomNumber(roomDTO.getRoomNumber());
        if (roomDTO.getAccommodationId() != null) room.setAccommodation(accommodationsDAO.findOne(roomDTO.getAccommodationId()));
        roomsDAO.update(room);
        return room;
    }

    public void delete(Integer id){
        Room room = roomsDAO.findOne(id);
        roomsDAO.delete(room);
    }
}
