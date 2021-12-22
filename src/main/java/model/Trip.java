package model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Builder
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
    @OneToOne
    private Bus buses ;

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
