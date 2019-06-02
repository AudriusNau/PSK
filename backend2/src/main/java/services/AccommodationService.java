package services;

import dto.AccommodationDTO;
import entities.*;
import interceptors.DevbridgeInterceptor;
import lombok.Getter;
import lombok.Setter;
import persistence.AccommodationsDAO;
import persistence.CalendarsDAO;
import persistence.OfficesDAO;
import persistence.RoomCalendarsDAO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@ApplicationScoped
@DevbridgeInterceptor
public class AccommodationService {

    @Inject
    @Setter
    @Getter
    private AccommodationsDAO accommodationsDAO;

    @Inject
    @Setter
    @Getter
    private OfficesDAO officesDAO;

    @Inject
    @Setter
    @Getter
    private RoomCalendarsDAO roomCalendarsDAO;

    @Inject
    @Setter
    @Getter
    private CalendarsDAO calendarsDAO;

    @Inject
    private RoomCalendarService roomCalendarService;

    @Inject
    private CalendarService calendarService;

    public List<Accommodation> getAll() {
        return accommodationsDAO.loadAll();
    }

    public Accommodation getById(Integer id) {
        return accommodationsDAO.findOne(id);
    }

    public List<Accommodation> getByOfficeId(Integer id) {
        return accommodationsDAO.findByOfficeId(id);
    }

    public Accommodation getApartments(Integer id) {
        return accommodationsDAO.getApartments(id);
    }

    public Office getOfficeByAccommodationId(Integer id) {
        return (accommodationsDAO.findOne(id)).getOffice();
    }

    public Accommodation create(AccommodationDTO accommodationDTO) {
        Accommodation accommodation = accommodationsDAO.create();
        accommodation.setName(accommodationDTO.getName());
        accommodation.setOffice(officesDAO.findOne(accommodationDTO.getOfficeId()));
        accommodation.setAccommodationType(accommodationDTO.getAccommodationType());
        accommodationsDAO.persist(accommodation);
        return accommodation;
    }

    public Accommodation update(Integer id, AccommodationDTO accommodationDTO) {
        Accommodation accommodation = accommodationsDAO.findOne(id);
        if (accommodation == null){
            throw new IllegalArgumentException("accommodation id "
                    + id + " not found");
        }
        if (accommodationDTO.getName() != null) accommodation.setName(accommodationDTO.getName());
        if (accommodationDTO.getOfficeId() != null) accommodation.setOffice(officesDAO.findOne(accommodationDTO.getOfficeId()));
        if (accommodationDTO.getAccommodationType() != null) accommodation.setAccommodationType(accommodationDTO.getAccommodationType());
        accommodationsDAO.update(accommodation);
        return accommodation;
    }

    public void delete(Integer id) {
        Accommodation accommodation = accommodationsDAO.findOne(id);
        accommodationsDAO.delete(accommodation);
    }

    public void bookAccommodation(EmployeeTravel employeeTravel){
        Accommodation apartments = accommodationsDAO.getApartments(employeeTravel.getTravel().getArrivalOffice().getId());
        Accommodation hotel = accommodationsDAO.getHotel(employeeTravel.getTravel().getArrivalOffice().getId());
        System.out.println(hotel.getName());
        List<Date> dates = calendarService.getDates(employeeTravel.getTravel().getStartDate(), employeeTravel.getTravel().getEndDate());
        bookRooms(apartments, dates);
        if (employeeTravel.getRoom() == null) bookRooms(hotel, dates);

    }

    public void bookRooms(Accommodation accommodation, List<Date> dates){
        RoomCalendar roomCalendar;
        DateFormat df = new SimpleDateFormat("yyyy_MM_dd");
        int days;
        for (Room room: accommodation.getRooms()) {
            days = 0;
            for (Date date: dates) {
                if(!roomCalendarService.isAvailable(df.format(date), room.getId())) break;
                else{
                    days++;
                }
            }
            if (dates.size() == days){
                for (Date date: dates
                ) {
                    roomCalendar = roomCalendarsDAO.create();
                    roomCalendar.setCalendar(calendarsDAO.getOrCreate(df.format(date)));
                    roomCalendar.setRoom(room);
                    roomCalendarsDAO.persist(roomCalendar);
                }
                break;
            }
        }
    }
}
