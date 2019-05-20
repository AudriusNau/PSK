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
import java.util.List;

@ApplicationScoped
@Path("/employee")
@Produces(MediaType.APPLICATION_JSON)
public class EmployeeController {

    @Inject
    @Setter
    @Getter
    private EmployeesDAO employeesDAO;

    @Path("/get/{employeeId}")
    @GET
    public Employee find(@PathParam("employeeId") int id){
        Employee employee = employeesDAO.findOne(id);
        return employee;
    }

    @Path("/get/all")
    @GET
    public List<Employee> find(){
        List<Employee> employees = employeesDAO.loadAll();
        return employees;
    }

    @Path("/post")
    @POST
    @Transactional
    public Employee create(@QueryParam("firstName") String firstName,
                           @QueryParam("lastName") String lastName,
                           @QueryParam("role") String role) {
        System.out.println("employee post");
        Employee employee = new Employee();
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setRole(role);
        employeesDAO.persist(employee);
        return employee;
    }

    @Path("/put/{id}")
    @PUT @Transactional
    public Response update(@PathParam("id") int id,
                           @QueryParam("firstName") String firstName,
                           @QueryParam("lastName") String lastName,
                           @QueryParam("role") String role) {

        Employee employee = employeesDAO.findOne(id);
        if (employee == null){
            throw new IllegalArgumentException("employee id "
                    + id + " not found");
        }
        if (firstName != null) employee.setFirstName(firstName);
        if (lastName != null) employee.setLastName(lastName);
        if (role != null) employee.setRole(role);
        employeesDAO.update(employee);
        return Response.ok(employee).build();
    }

}
