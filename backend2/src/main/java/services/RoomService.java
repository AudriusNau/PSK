package services;

import dto.RoomDTO;
import entities.Accommodation;
import entities.Room;
import lombok.Getter;
import lombok.Setter;
import persistence.AccommodationsDAO;
import persistence.EmployeeCalendarsDAO;
import persistence.RoomCalendarsDAO;
import persistence.RoomsDAO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ApplicationScoped
public class RoomService {

    @Inject
    @Setter
    @Getter
    private RoomsDAO roomsDAO;

    @Inject
    @Setter
    @Getter
    private RoomCalendarsDAO roomCalendarsDAO;

    @Inject
    @Setter
    @Getter
    private AccommodationsDAO accommodationsDAO;

    @Inject
    @Setter
    @Getter
    private EmployeeCalendarsDAO employeeCalendarsDAO;

    @Inject
    private CalendarService calendarService;

    public List<Room> getAll(){
        return roomsDAO.loadAll();
    }

    public List<Room> getByAccommodationId(Integer id){
        return roomsDAO.findByAccommodationId(id);
    }

    public ArrayList<Room> getAvailableRooms(List<Accommodation> accommodations, String startDate, String endDate){
        ArrayList<Room> availableRooms = new ArrayList<Room>();
        List<Date> dates = calendarService.getDates(startDate, endDate);
        int i=0;
        int k=0;
        for (Accommodation accommodation: accommodations)
        {
            List<Room> rooms = getByAccommodationId(accommodation.getId());
            for (Room room: rooms) {
                i=0;
                k=0;
                for(Date date: dates) {
                    i++;
                    try{roomCalendarsDAO.findByRoomAndDate(room.getId(), date.toString());}
                    catch (NoResultException e){
                        k++;

                    }

                    //if (roomCalendarsDAO.findByRoomAndDate(room.getId(), date.toString()) == null) availableRooms.add(room);
                }
                if (i==k) availableRooms.add(room);
            }
        }
        return availableRooms;
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
