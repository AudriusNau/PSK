package entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NamedQueries({
        @NamedQuery(name = "EmployeeTravel.findAll", query = "select t from EmployeeTravel as t"),
        @NamedQuery(name = "EmployeeTravel.findByTravelId", query = "select t from EmployeeTravel as t where t.travel = :travel"),
        @NamedQuery(name = "EmployeeTravel.findByEmployee", query = "select t from EmployeeTravel as t where t.employee = :employee")
})
@Table(name = "EMPLOYEE_TRAVEL")
@Getter
@Setter
public class EmployeeTravel {

    public EmployeeTravel(){

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private int id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    Employee employee;

    @ManyToOne
    @JoinColumn(name = "travel_id")
    Travel travel;

    @ManyToOne
    @JoinColumn(name = "flight_id")
    Flight flight;

    @ManyToOne
    @JoinColumn(name = "car_rent_id")
    CarRent carRent;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EmployeeTravel that = (EmployeeTravel) o;

        if (id != that.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
