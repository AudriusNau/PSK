package entities;

import lombok.Getter;
import lombok.Setter;
import org.apache.johnzon.mapper.JohnzonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "Flight.findAll", query = "select t from Flight as t")
})
@Table(name = "FLIGHT")
@Getter
@Setter
public class Flight implements Serializable {

    public Flight(){

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private int id;

    @Column(name = "NEED", nullable = true)
    private Integer need;

    @Column(name = "INFO", nullable = true, length = 50)
    private String info;

    @JohnzonIgnore
    @OneToMany(mappedBy = "flight")
    List<EmployeeTravel> employeeTravels;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Flight flight = (Flight) o;

        if (id != flight.id) return false;
        if (need != null ? !need.equals(flight.need) : flight.need != null) return false;
        if (info != null ? !info.equals(flight.info) : flight.info != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (need != null ? need.hashCode() : 0);
        result = 31 * result + (info != null ? info.hashCode() : 0);
        return result;
    }
}
