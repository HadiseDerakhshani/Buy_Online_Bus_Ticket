package model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;


@Data
@NoArgsConstructor
@Entity
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Temporal(TemporalType.DATE)
    private Date moveDate;
    @Temporal(TemporalType.TIME)
    private Date moveTime;
    private double price;
    private String origin;
    private String destination;
    @ManyToOne
    private Bus buses;
    private int numOfReserve;
    private int numOfEmpty = emptySeat();

    private int emptySeat() {
        return buses.getNumOfSeat() - this.numOfReserve;
    }


    @Override
    public String toString() {
        return "Trip{" +
                ", moveDate=" + moveDate +
                ", moveTime=" + moveTime +
                ", price=" + price +
                ", origin='" + origin + '\'' +
                ", destination='" + destination + '\'' +
                ", buses=" + buses +
                '}';
    }
}
