package entities;

import lombok.Getter;
import lombok.Setter;
import org.apache.johnzon.mapper.JohnzonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "CarRent.findAll", query = "select t from CarRent as t")
})
@Table(name = "CAR_RENT")
@Getter
@Setter
public class CarRent {

    public CarRent(){

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
    @OneToMany(mappedBy = "carRent")
    List<EmployeeTravel> employeeTravels;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CarRent carRent = (CarRent) o;

        if (id != carRent.id) return false;
        if (need != null ? !need.equals(carRent.need) : carRent.need != null) return false;
        if (info != null ? !info.equals(carRent.info) : carRent.info != null) return false;

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
