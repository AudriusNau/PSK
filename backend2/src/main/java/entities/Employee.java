package entities;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.apache.johnzon.mapper.JohnzonIgnore;

import java.io.Serializable;
import java.util.*;

@Entity
@NamedQueries({
        @NamedQuery(name = "Employee.findAll", query = "select t from Employee as t")
})
@Table(name = "EMPLOYEE")
@Getter @Setter
public class Employee implements Serializable {

    public Employee(){

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private int id;

    @Column(name = "FIRST_NAME", nullable = true, length = 20)
    private String firstName;

    @Column(name = "LAST_NAME", nullable = true, length = 20)
    private String lastName;

    @Column(name = "ROLE", nullable = true, length = 20)
    private String role;

    @JohnzonIgnore
    @OneToMany(mappedBy = "employee")
    List<EmployeeCalendar> employeeCalendars;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employee employee = (Employee) o;

        if (id != employee.id) return false;
        if (firstName != null ? !firstName.equals(employee.firstName) : employee.firstName != null) return false;
        if (lastName != null ? !lastName.equals(employee.lastName) : employee.lastName != null) return false;
        if (role != null ? !role.equals(employee.role) : employee.role != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        return result;
    }
}
