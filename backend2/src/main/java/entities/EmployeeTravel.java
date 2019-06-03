package entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NamedQueries({
        @NamedQuery(name = "EmployeeTravel.findAll", query = "select t from EmployeeTravel as t"),
        @NamedQuery(name = "EmployeeTravel.findByTravelId", query = "select t from EmployeeTravel as t where t.travel = :travel"),
        @NamedQuery(name = "EmployeeTravel.findByEmployee", query = "select t from EmployeeTravel as t where t.employee = :employee"),
        @NamedQuery(name = "EmployeeTravel.findByFlight", query = "select t from EmployeeTravel as t where t.flight = :flight"),
        @NamedQuery(name = "EmployeeTravel.findByCarRent", query = "select t from EmployeeTravel as t where t.carRent = :carRent"),
        @NamedQuery(name = "EmployeeTravel.findByRoom", query = "select t from EmployeeTravel as t where t.room = :room"),
        @NamedQuery(name = "EmployeeTravel.findAllPendingTravels", query = "select t from EmployeeTravel as t where t.status = false"),
        @NamedQuery(name = "EmployeeTravel.findAllAcceptedTravels", query = "select t from EmployeeTravel as t where t.status = true"),
        @NamedQuery(name = "EmployeeTravel.findAllPendingTravelsForEmployee", query = "select t from EmployeeTravel as t where t.status = false AND t.employee = :employee"),
        @NamedQuery(name = "EmployeeTravel.findAllAcceptedTravelsForEmployee", query = "select t from EmployeeTravel as t where t.status = true AND t.employee = :employee")


})
@Table(name = "EMPLOYEE_TRAVEL", schema = "PUBLIC", catalog = "DEVBRIDGE")
@Getter
@Setter
public class EmployeeTravel implements Serializable {

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
    @ManyToOne
    @JoinColumn(name = "room_id")
    Room room;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private int id;

    @Column(name = "STATUS", nullable = false)
    private boolean status;

    public EmployeeTravel(){

    }

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
