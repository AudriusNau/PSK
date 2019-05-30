package rest;

import dto.EmployeeDTO;
import entities.Employee;
import interceptors.DevbridgeInterceptor;
import services.EmployeeService;

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
@DevbridgeInterceptor
public class EmployeeController {

    @Inject
    private EmployeeService employeeService;

    @Path("/get/{employeeId}")
    @GET
    public Employee find(@PathParam("employeeId") int id){
        return employeeService.findById(id);
    }

    @Path("/get/all")
    @GET
    public List<Employee> find(){
        return employeeService.getAll();
    }

    @Path("/post")
    @POST
    @Transactional
    public Employee create(EmployeeDTO employeeDTO) {
        return employeeService.create(employeeDTO);
    }

    @Path("/put/{id}")
    @PUT @Transactional
    public Response update(@PathParam("id") int id,
                           EmployeeDTO employeeDTO) {

        Employee employee = employeeService.update(id, employeeDTO);
        return Response.ok(employee).build();
    }

    @Path("/delete/{id}")
    @DELETE @Transactional
    public Response delete(@PathParam("id") int id) {
        employeeService.delete(id);
        return Response.ok().build();
    }

}
