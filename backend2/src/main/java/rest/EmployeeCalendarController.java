package rest;

import dto.EmployeeCalendarDTO;
import entities.EmployeeCalendar;
import entities.Calendar;
import interceptors.DevbridgeInterceptor;
import lombok.Getter;
import lombok.Setter;
import persistence.EmployeeCalendarsDAO;
import persistence.EmployeesDAO;
import persistence.CalendarsDAO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
@Path("/employeeCalendar")
@Produces(MediaType.APPLICATION_JSON)
@DevbridgeInterceptor
public class EmployeeCalendarController {

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

    /*@Path("/get/date/{date}")
    @GET
    public EmployeeCalendar findByDate(@PathParam("date") String date){
        EmployeeCalendar employeeCalendar = employeeCalendarsDAO.findByDate(date);
        System.out.println(employeeCalendar.getCalendar());
        return employeeCalendar;//pakeiciam i lista
    }*/

    @Path("/get/date/{date}")
    @GET
    public List <EmployeeCalendar> findByDate(@PathParam("date") String date){
        List<EmployeeCalendar> employeeCalendars = employeeCalendarsDAO.findByDate(date);
        return employeeCalendars;
    }

    @Path("/get/employeeId/{employeeId}")
    @GET
    public List <EmployeeCalendar> findByDate(@PathParam("employeeId") Integer employeeId){
        List<EmployeeCalendar> employeeCalendars = employeeCalendarsDAO.findByEmployee(employeeId);
        return employeeCalendars;
    }

    @Path("/get/all")
    @GET
    public List<EmployeeCalendar> find(){
        List<EmployeeCalendar> employeeCalendars = employeeCalendarsDAO.loadAll();
        return employeeCalendars;
    }

    @Path("/post")
    @POST
    @Transactional
    public EmployeeCalendar create(EmployeeCalendarDTO employeeCalendarDTO) {
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

    @Path("/delete/{id}")
    @DELETE @Transactional
    public Response delete(@PathParam("id") int id) {
        EmployeeCalendar employeeCalendar = employeeCalendarsDAO.findOne(id);
        employeeCalendarsDAO.delete(employeeCalendar);
        return Response.ok().build();
    }

}
