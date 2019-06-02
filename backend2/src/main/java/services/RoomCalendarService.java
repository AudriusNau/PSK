package services;

import dto.RoomCalendarDTO;
import entities.Calendar;
import entities.RoomCalendar;
import interceptors.DevbridgeInterceptor;
import lombok.Getter;
import lombok.Setter;
import persistence.CalendarsDAO;
import persistence.RoomCalendarsDAO;
import persistence.RoomsDAO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
@DevbridgeInterceptor
public class RoomCalendarService {

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

    public List<RoomCalendar> getAll(){
        return roomCalendarsDAO.loadAll();
    }

    public List<RoomCalendar> getByDate(String date){
        return roomCalendarsDAO.findByDate(date);
    }

    public List<RoomCalendar> getByRoom(Integer id){
        return roomCalendarsDAO.findByRoom(id);
    }

    public boolean isAvailable(String date, Integer id){

        try{
            if(roomCalendarsDAO.findByRoomAndDate(id, date) != null){
                System.out.println(id);
                System.out.println(roomCalendarsDAO.findByRoomAndDate(id, date).getRoom().getId());
                System.out.println(date);
                System.out.println(roomCalendarsDAO.findByRoomAndDate(id, date).getCalendar().getDate());
                return false;
            }
            else return true;
        } catch (Exception e){
            System.out.println("found exception");
            return true;
        }
    }

    public RoomCalendar create(RoomCalendarDTO roomCalendarDTO){
        RoomCalendar roomCalendar = roomCalendarsDAO.create();
        if (calendarsDAO.findOne(roomCalendarDTO.getDate()) == null) {
            System.out.println("date not found");
            Calendar calendar = calendarsDAO.create();
            calendar.setDate(roomCalendarDTO.getDate());
            calendarsDAO.persist(calendar);
        }
        roomCalendar.setCalendar(calendarsDAO.findOne(roomCalendarDTO.getDate()));
        roomCalendar.setRoom(roomsDAO.findOne(roomCalendarDTO.getRoomId()));
        roomCalendarsDAO.persist(roomCalendar);
        return roomCalendar;
    }

    public void delete(Integer id){
        RoomCalendar roomCalendar = roomCalendarsDAO.findOne(id);
        roomCalendarsDAO.delete(roomCalendar);
    }

}
