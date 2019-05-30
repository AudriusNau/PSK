package services;

import dto.EmployeeDTO;
import entities.Employee;
import entities.EmployeeCalendar;
import lombok.Getter;
import lombok.Setter;
import persistence.EmployeesDAO;
import security.boundary.HashGenerator;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class EmployeeService {

    @Inject
    @Setter
    @Getter
    private EmployeesDAO employeesDAO;

    @Inject
    HashGenerator passwordHash;

    public List<Employee> getAll(){
        return employeesDAO.loadAll();
    }

    public Employee findById(Integer id){
        return employeesDAO.findOne(id);
    }

    public Employee create(EmployeeDTO employeeDTO){
        Employee employee = employeesDAO.create();
        employee.setFirstName(employeeDTO.getFirstName());
        employee.setLastName(employeeDTO.getLastName());
        employee.setRole(employeeDTO.getRole());
        employee.setUsername(employeeDTO.getUsername());
        employee.setPassword(passwordHash.getHashedText(employeeDTO.getPassword()));
        employeesDAO.persist(employee);
        return employee;
    }

    public Employee update(Integer id, EmployeeDTO employeeDTO){
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
        return employee;
    }

    public void delete(Integer id){
        Employee employee = employeesDAO.findOne(id);
        employeesDAO.delete(employee);
    }

}
