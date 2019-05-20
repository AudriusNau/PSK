package entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NamedQueries({
        @NamedQuery(name = "RoomCalendar.findAll", query = "select t from RoomCalendar as t"),
        @NamedQuery(name = "RoomCalendar.findByDate", query = "select t from RoomCalendar as t where t.calendar = :date"),
        @NamedQuery(name = "RoomCalendar.findByRoom", query = "select t from RoomCalendar as t where t.room = :roomId")
})
@Table(name = "ROOM_CALENDAR", schema = "PUBLIC", catalog = "DEVBRIDGE")
@Getter
@Setter
public class RoomCalendar implements Serializable {

    public RoomCalendar(){

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private int id;

    //@JohnzonIgnore
    @ManyToOne
    @JoinColumn(name = "date")
    Calendar calendar;

    //@JohnzonIgnore
    @ManyToOne
    @JoinColumn(name = "room_id")
    Room room;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoomCalendar that = (RoomCalendar) o;

        if (id != that.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
