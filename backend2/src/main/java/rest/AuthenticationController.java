package rest;

import entities.Employee;
import interceptors.DevbridgeInterceptor;
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
@Path("/authentication")
@Produces(MediaType.APPLICATION_JSON)
@DevbridgeInterceptor
public class AuthenticationController {

    @Inject
    @Setter
    @Getter
    private EmployeesDAO employeesDAO;

    @Path("/get/getEmployeeByUsernameAndPassword")
    @GET
    public Employee find(@QueryParam("username") String username,
                     @QueryParam("password") String password){
        Employee employee = employeesDAO.findByUsernameAndPassword(username, password);
        return employee;
    }

}
