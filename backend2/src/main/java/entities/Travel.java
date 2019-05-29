package entities;

import lombok.Getter;
import lombok.Setter;
import org.apache.johnzon.mapper.JohnzonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "Travel.findAll", query = "select t from Travel as t"),
        @NamedQuery(name = "Travel.findByDepartureOfficeId", query = "select t from Travel as t where t.departureOffice = :departureOffice"),
        @NamedQuery(name = "Travel.findByArrivalOfficeId", query = "select t from Travel as t where t.arrivalOffice = :arrivalOffice"),
        @NamedQuery(name = "Travel.findByOrganiserId", query = "select t from Travel as t where t.organiser = :organiser")
})
@Table(name = "TRAVEL")
@Getter
@Setter
public class Travel implements Serializable {

    public Travel(){

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private int id;

    @Column(name = "START_DATE", nullable = true, length = 20)
    private String startDate;

    @Column(name = "END_DATE", nullable = true, length = 20)
    private String endDate;

    @Column(name = "PRICE", nullable = true, precision = 0)
    private Double price;

    @JohnzonIgnore
    @OneToMany(mappedBy = "travel")
    List<EmployeeTravel> employeeTravels;

    @JohnzonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "DEPARTURE_OFFICE_ID", nullable = true)
    private Office departureOffice;

    @JohnzonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ARRIVAL_OFFICE_ID", nullable = true)
    private Office arrivalOffice;

    @JohnzonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ORGANISER_ID", nullable = true)
    private Employee organiser;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Travel travel = (Travel) o;

        if (id != travel.id) return false;
        if (startDate != null ? !startDate.equals(travel.startDate) : travel.startDate != null) return false;
        if (endDate != null ? !endDate.equals(travel.endDate) : travel.endDate != null) return false;
        if (price != null ? !price.equals(travel.price) : travel.price != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        return result;
    }
}
