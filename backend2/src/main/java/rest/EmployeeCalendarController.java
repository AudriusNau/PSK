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
import services.EmployeeCalendarService;

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
    private EmployeeCalendarService employeeCalendarService;

    @Path("/get/date/{date}")
    @GET
    public List <EmployeeCalendar> findByDate(@PathParam("date") String date){
        return employeeCalendarService.getByDate(date);
    }

    @Path("/get/employeeId/{employeeId}")
    @GET
    public List <EmployeeCalendar> findByDate(@PathParam("employeeId") Integer employeeId){
        return employeeCalendarService.getByEmployeeId(employeeId);
    }

    @Path("/get/all")
    @GET
    public List<EmployeeCalendar> find(){
        return employeeCalendarService.getAll();
    }

    @Path("/post")
    @POST
    @Transactional
    public EmployeeCalendar create(EmployeeCalendarDTO employeeCalendarDTO) {
        EmployeeCalendar employeeCalendar = employeeCalendarService.create(employeeCalendarDTO);
        return employeeCalendar;
    }

    @Path("/delete/{id}")
    @DELETE @Transactional
    public Response delete(@PathParam("id") int id) {
        employeeCalendarService.delete(id);
        return Response.ok().build();
    }

}
