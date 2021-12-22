package model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Builder
@NoArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int SeatCount;
    @CreationTimestamp
    private Date recordReservation;
    @ManyToOne
    private Trip trip;
    @OneToOne
    private  Passenger passenger;

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", SeatCount=" + SeatCount +
                ", recordReservation=" + recordReservation +
                ", trip=" + trip +
                '}';
    }
}
