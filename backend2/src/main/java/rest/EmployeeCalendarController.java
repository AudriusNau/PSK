package rest;

import entities.EmployeeCalendar;
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

    @Path("/get/employee/{employeeId}")
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
    public EmployeeCalendar create(@QueryParam("date") String date,
                           @QueryParam("employeeId") Integer employeeId) {
        System.out.println("employeeCalendar post");
        EmployeeCalendar employeeCalendar = new EmployeeCalendar();
        /*employeeCalendar.setDate(date);
        employeeCalendar.setEmployeeId(employeeId);*/
        employeeCalendar.setCalendar(calendarsDAO.findOne(date));
        employeeCalendar.setEmployee(employeesDAO.findOne(employeeId));
        System.out.println("employeeCalendar set");
        employeeCalendarsDAO.persist(employeeCalendar);
        return employeeCalendar;
    }

    /*@Path("/put/{id}")
    @PUT @Transactional
    public Response update(@PathParam("id") int id,
                           @QueryParam("first_name") String firstName,
                           @QueryParam("last_name") String lastName,
                           @QueryParam("role") String role) {

        EmployeeCalendar employeeCalendar = employeeCalendarsDAO.findOne(id);
        if (employeeCalendar == null){
            throw new IllegalArgumentException("employeeCalendar id "
                    + id + " not found");
        }
        employeeCalendar.setFirstName(firstName);
        employeeCalendar.setLastName(lastName);
        employeeCalendar.setRole(role);
        employeeCalendarsDAO.update(employeeCalendar);
        return Response.ok(employeeCalendar).build();
    }*/

}
