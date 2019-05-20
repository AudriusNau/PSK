package entities;

import lombok.Getter;
import lombok.Setter;
import org.apache.johnzon.mapper.JohnzonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "Accommodation.findAll", query = "select t from Accommodation as t")
        ,
        @NamedQuery(name = "Accommodation.findByOfficeId", query = "select t from Accommodation as t where t.office = :office")
})
@Table(name = "ACCOMMODATION")
@Getter
@Setter
public class Accommodation implements Serializable {

    public Accommodation(){

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private int id;

    @Column(name = "NAME", nullable = false, length = 20)
    private String name;

    @Column(name = "ACCOMMODATION_TYPE", nullable = false, length = 20)
    private String accommodationType;

    @JohnzonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "OFFICE_ID", nullable = true)
    private Office office;

    @JohnzonIgnore
    @OneToMany(mappedBy = "accommodation")
    private List<Room> rooms = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Accommodation that = (Accommodation) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
