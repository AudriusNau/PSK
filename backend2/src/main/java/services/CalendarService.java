package services;

import dto.CalendarDTO;
import entities.Calendar;
import interceptors.DevbridgeInterceptor;
import lombok.Getter;
import lombok.Setter;
import persistence.CalendarsDAO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
@DevbridgeInterceptor
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

    public List<Date> getDates(String startDateString, String endDateString){
        Date startDate = null;
        Date endDate = null;
        try {
            startDate=new SimpleDateFormat("yyyy_MM_dd").parse(startDateString);
            endDate=new SimpleDateFormat("yyyy_MM_dd").parse(endDateString);
        }
        catch (Exception e) {
        }
        List<Date> datesInRange = new ArrayList<>();
        java.util.Calendar calendar = new GregorianCalendar();
        calendar.setTime(startDate);

        java.util.Calendar endCalendar = new GregorianCalendar();
        endCalendar.setTime(endDate);

        while (calendar.before(endCalendar)) {
            Date result = calendar.getTime();
            datesInRange.add(result);
            calendar.add(java.util.Calendar.DATE, 1);
        }
        return datesInRange;
    }

    public Date parseDate(String date) throws Exception{
        return new SimpleDateFormat("yyyy_MM_dd").parse(date);
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
