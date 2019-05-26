package rest;

import dto.EmployeeDTO;
import entities.Employee;
import interceptors.DevbridgeInterceptor;
import lombok.Getter;
import lombok.Setter;
import persistence.EmployeesDAO;
import security.boundary.HashGenerator;
import security.entity.HashServiceType;

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
    @Setter
    @Getter
    private EmployeesDAO employeesDAO;

    @Inject
    @HashServiceType(HashServiceType.HashType.PBKDF)
    HashGenerator passwordHash;

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
    public Employee create(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setFirstName(employeeDTO.getFirstName());
        employee.setLastName(employeeDTO.getLastName());
        employee.setRole(employeeDTO.getRole());
        employee.setUsername(employeeDTO.getUsername());
        employee.setPassword(passwordHash.getHashedText(employeeDTO.getPassword()));
        employeesDAO.persist(employee);
        return employee;
    }

    @Path("/put/{id}")
    @PUT @Transactional
    public Response update(@PathParam("id") int id,
                           EmployeeDTO employeeDTO) {

        Employee employee = employeesDAO.findOne(id);
        if (employee == null){
            throw new IllegalArgumentException("employee id "
                    + id + " not found");
        }
        if (employeeDTO.getFirstName() != null) employee.setFirstName(employeeDTO.getFirstName());
        if (employeeDTO.getLastName() != null) employee.setLastName(employeeDTO.getLastName());
        if (employeeDTO.getRole() != null) employee.setRole(employeeDTO.getRole());
        if (employeeDTO.getUsername() != null) employee.setUsername(employeeDTO.getUsername());
        if (employeeDTO.getPassword() != null) employee.setPassword(passwordHash.getHashedText(employeeDTO.getPassword()));
        employeesDAO.update(employee);
        return Response.ok(employee).build();
    }

    @Path("/delete/{id}")
    @DELETE @Transactional
    public Response delete(@PathParam("id") int id) {
        Employee employee = employeesDAO.findOne(id);
        employeesDAO.delete(employee);
        return Response.ok().build();
    }

}
