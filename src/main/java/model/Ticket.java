package model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int SeatCount;
    @CreationTimestamp
    private Date recordReservation;
    @ManyToOne(cascade = CascadeType.ALL)
    private Trip trip;
    @OneToOne(cascade = CascadeType.ALL)
    private Passenger passenger;

    public Ticket() {
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", SeatCount=" + SeatCount +
                ", recordReservation=" + recordReservation +
                ", trip=" + trip +
                ", passenger=" + passenger +
                '}';
    }
}
