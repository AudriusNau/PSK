package entities;

import lombok.Getter;
import lombok.Setter;
import org.apache.johnzon.mapper.JohnzonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "Calendar.findAll", query = "select t from Calendar as t")
})
@Table(name = "CALENDAR")
@Getter
@Setter
public class Calendar implements Serializable {

    public Calendar(){

    }

    @Id
    @Column(name = "DATE", nullable = false, length = 20)
    private String date;

    @JohnzonIgnore
    @OneToMany(mappedBy = "calendar")
    List<EmployeeCalendar> employeeCalendars;

    @JohnzonIgnore
    @OneToMany(mappedBy = "calendar")
    List<RoomCalendar> roomCalendars;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Calendar calendar = (Calendar) o;

        if (date != null ? !date.equals(calendar.date) : calendar.date != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return date != null ? date.hashCode() : 0;
    }
}
