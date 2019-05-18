package rest;

import entities.Employee;
import lombok.Getter;
import lombok.Setter;
import persistence.EmployeesDAO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationScoped
@Path("/employee")
@Produces(MediaType.APPLICATION_JSON)
public class EmployeeController {

    @Inject
    @Setter
    @Getter
    private EmployeesDAO employeesDAO;

    @Path("/show/{employeeId}")
    @GET
    public Employee find(@PathParam("employeeId") int id){
        Employee employee = employeesDAO.findOne(id);
        return employee;
    }

    @Path("/create")
    @POST
    @Transactional
    public Employee create(@QueryParam("first_name") String firstName,
                           @QueryParam("last_name") String lastName,
                           @QueryParam("role") String role) {
        System.out.println("employee post");
        Employee employee = new Employee();
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setRole(role);
        employeesDAO.persist(employee);
        return employee;
    }

    @Path("/update/{id}")
    @PUT @Transactional
    public Response update(@PathParam("id") int id,
                           @QueryParam("first_name") String firstName,
                           @QueryParam("last_name") String lastName,
                           @QueryParam("role") String role) {

        Employee employee = employeesDAO.findOne(id);
        if (employee == null){
            throw new IllegalArgumentException("employee id "
                    + id + " not found");
        }
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setRole(role);
        employeesDAO.update(employee);
        return Response.ok(employee).build();
    }

}
