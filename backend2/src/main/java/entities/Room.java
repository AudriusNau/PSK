package entities;

import lombok.Getter;
import lombok.Setter;
import org.apache.johnzon.mapper.JohnzonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "Room.findAll", query = "select t from Room as t"),
        @NamedQuery(name = "Room.findByAccommodationId", query = "select t from Room as t where t.accommodation = :accommodation")

})
@Table(name = "ROOM")
@Getter
@Setter
public class Room implements Serializable {

    public Room(){

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private int id;

    @JohnzonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ACCOMMODATION_ID", nullable = true)
    private Accommodation accommodation;

    @Column(name = "ROOM_NUMBER", nullable = true)
    private Integer roomNumber;

    @JohnzonIgnore
    @OneToMany(mappedBy = "room")
    List<RoomCalendar> roomCalendars;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Room room = (Room) o;

        if (id != room.id) return false;
        if (accommodation != null ? !accommodation.equals(room.accommodation) : room.accommodation != null)
            return false;
        if (roomNumber != null ? !roomNumber.equals(room.roomNumber) : room.roomNumber != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (accommodation != null ? accommodation.hashCode() : 0);
        result = 31 * result + (roomNumber != null ? roomNumber.hashCode() : 0);
        return result;
    }
}
