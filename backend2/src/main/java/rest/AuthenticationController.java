package rest;

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
@Path("/authentication")
@Produces(MediaType.APPLICATION_JSON)
@DevbridgeInterceptor
public class AuthenticationController {

    @Inject
    @Setter
    @Getter
    private EmployeesDAO employeesDAO;

    @Inject
    @HashServiceType(HashServiceType.HashType.PBKDF)
    HashGenerator passwordHash;

    @Path("/get/getEmployeeByUsernameAndPassword")
    @GET
    public Employee find(@QueryParam("username") String username,
                     @QueryParam("password") String password){
        Employee employee = employeesDAO.findByUsername(username);
        if (passwordHash.isHashedTextMatch(password, employee.getPassword())) return employee;
        else return null;
    }

}
