package entities;

import lombok.Getter;
import lombok.Setter;
import org.apache.johnzon.mapper.JohnzonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "Office.findAll", query = "select t from Office as t")
})
@Table(name = "OFFICE")
@Getter
@Setter
public class Office implements Serializable {

    public Office(){

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private int id;

    @Column(name = "NAME", nullable = true, length = 20)
    private String name;

    @JohnzonIgnore
    @OneToMany(mappedBy = "office")
    private List<Accommodation> accommodations = new ArrayList<>();

    @JohnzonIgnore
    @OneToMany(mappedBy = "departureOffice")
    private List<Travel> travelsFrom = new ArrayList<>();

    @JohnzonIgnore
    @OneToMany(mappedBy = "arrivalOffice")
    private List<Travel> travelsTo = new ArrayList<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Office office = (Office) o;

        if (id != office.id) return false;
        if (name != null ? !name.equals(office.name) : office.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
