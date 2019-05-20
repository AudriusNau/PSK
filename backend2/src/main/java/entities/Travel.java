package entities;

import lombok.Getter;
import lombok.Setter;
import org.apache.johnzon.mapper.JohnzonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NamedQueries({
        @NamedQuery(name = "Travel.findAll", query = "select t from Travel as t"),
        @NamedQuery(name = "Travel.findByDepartureOfficeId", query = "select t from Travel as t where t.departureOffice = :departureOffice"),
        @NamedQuery(name = "Travel.findByArrivalOfficeId", query = "select t from Travel as t where t.arrivalOffice = :arrivalOffice")
})
@Table(name = "TRAVEL")
@Getter
@Setter
public class Travel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private int id;

    @Column(name = "DATE", nullable = true, length = 20)
    private String date;

    @Column(name = "PRICE", nullable = true, precision = 0)
    private Double price;

    @JohnzonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "DEPARTURE_OFFICE_ID", nullable = true)
    private Office departureOffice;

    @JohnzonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ARRIVAL_OFFICE_ID", nullable = true)
    private Office arrivalOffice;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Travel travel = (Travel) o;

        if (id != travel.id) return false;
        if (date != null ? !date.equals(travel.date) : travel.date != null) return false;
        if (price != null ? !price.equals(travel.price) : travel.price != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        return result;
    }
}
