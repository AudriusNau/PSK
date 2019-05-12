package main.backend.entities;

import javax.persistence.*;
import java.io.Serializable;

import java.util.Date;
import java.util.Objects;

@Entity
@NamedQueries({
        @NamedQuery(name = "Travel.findAll", query = "select b from Travel as b")
})
@Table(name = "TRAVEL")
public class Travel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "DEPARTURE_DATE")
    private Date date;

    public Travel(){}

    public Travel(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Travel travel = (Travel) o;
        return Objects.equals(id, travel.id) &&
                Objects.equals(date, travel.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date);
    }


}
