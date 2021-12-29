package model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.enums.BusType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
public class Bus {

    @Id
    private int id;
    private int plaque;
    private String company;
    private int numOfSeat;
    @Enumerated(EnumType.STRING)
    private BusType busType;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Driver> driverList = new ArrayList<>();


    @Override
    public String toString() {
        return "Bus{" +
                ", company='" + company + '\'' +
                ", busType=" + busType +
                '}';
    }

}
