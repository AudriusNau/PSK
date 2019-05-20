package entities;

import lombok.Getter;
import lombok.Setter;
import org.apache.johnzon.mapper.JohnzonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NamedQueries({
        @NamedQuery(name = "EmployeeCalendar.findAll", query = "select t from EmployeeCalendar as t"),
        @NamedQuery(name = "EmployeeCalendar.findByDate", query = "select t from EmployeeCalendar as t where t.calendar = :date"),
        @NamedQuery(name = "EmployeeCalendar.findByEmployee", query = "select t from EmployeeCalendar as t where t.employee = :employeeId")
})
@Table(name = "EMPLOYEE_CALENDAR", schema = "PUBLIC", catalog = "DEVBRIDGE")
@Getter
@Setter
public class EmployeeCalendar implements Serializable {

    public EmployeeCalendar(){
        System.out.println("employeeCalendar constructor");
    }

    //@JohnzonIgnore
    @ManyToOne
    @JoinColumn(name = "date")
    Calendar calendar;

    //@JohnzonIgnore
    @ManyToOne
    @JoinColumn(name = "employee_id")
    Employee employee;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private int id;

    /*private String date = calendar.getDate();
    private int employeeId = employee.getId();*/

    /*@Column(name = "DATE", nullable = true, length = 20)
    private String date;

    @Column(name = "EMPLOYEE_ID", nullable = true)
    private Integer employeeId;*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EmployeeCalendar that = (EmployeeCalendar) o;

        if (id != that.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }


}
