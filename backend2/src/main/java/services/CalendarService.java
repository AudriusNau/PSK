package services;

import dto.CalendarDTO;
import entities.Calendar;
import lombok.Getter;
import lombok.Setter;
import persistence.CalendarsDAO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class CalendarService {

    @Inject
    @Setter
    @Getter
    private CalendarsDAO calendarsDAO;

    public List<Calendar> getAll(){
        return calendarsDAO.loadAll();
    }

    public Calendar getByDate(String date){
        return calendarsDAO.findOne(date);
    }

    public Calendar create(CalendarDTO calendarDTO){
        Calendar calendar = calendarsDAO.create();
        calendar.setDate(calendarDTO.getDate());
        calendarsDAO.persist(calendar);
        return calendar;
    }

    public void delete(String date){
        Calendar calendar = calendarsDAO.findOne(date);
        calendarsDAO.delete(calendar);
    }

}
