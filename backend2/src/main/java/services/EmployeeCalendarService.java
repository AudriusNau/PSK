package services;

import dto.EmployeeCalendarDTO;
import entities.Calendar;
import entities.EmployeeCalendar;
import lombok.Getter;
import lombok.Setter;
import persistence.CalendarsDAO;
import persistence.EmployeeCalendarsDAO;
import persistence.EmployeesDAO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class EmployeeCalendarService {

    @Inject
    @Setter
    @Getter
    private EmployeeCalendarsDAO employeeCalendarsDAO;

    @Inject
    @Setter
    @Getter
    private EmployeesDAO employeesDAO;

    @Inject
    @Setter
    @Getter
    private CalendarsDAO calendarsDAO;

    public List<EmployeeCalendar> getAll(){
        return employeeCalendarsDAO.loadAll();
    }

    public List<EmployeeCalendar> getByDate(String date){
        return employeeCalendarsDAO.findByDate(date);
    }

    public List<EmployeeCalendar> getByEmployeeId(Integer id){
        return employeeCalendarsDAO.findByEmployee(id);
    }

    public EmployeeCalendar create(EmployeeCalendarDTO employeeCalendarDTO){
        EmployeeCalendar employeeCalendar = employeeCalendarsDAO.create();
        if (calendarsDAO.findOne(employeeCalendarDTO.getDate()) == null) {
            System.out.println("date not found");
            Calendar calendar = calendarsDAO.create();
            calendar.setDate(employeeCalendarDTO.getDate());
            calendarsDAO.persist(calendar);
        }
        employeeCalendar.setCalendar(calendarsDAO.findOne(employeeCalendarDTO.getDate()));
        employeeCalendar.setEmployee(employeesDAO.findOne(employeeCalendarDTO.getEmployeeId()));
        employeeCalendarsDAO.persist(employeeCalendar);
        return employeeCalendar;
    }

    public void delete(Integer id){
        EmployeeCalendar employeeCalendar = employeeCalendarsDAO.findOne(id);
        employeeCalendarsDAO.delete(employeeCalendar);
    }
}
