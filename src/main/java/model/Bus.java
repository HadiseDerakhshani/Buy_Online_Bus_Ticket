package model;


import lombok.Data;
import model.enums.BusType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Bus {
    @Id
    private int plaque;
    private String company;
    private int numOfSeat;
    @Enumerated(EnumType.STRING)
    private BusType busType;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bus", fetch = FetchType.LAZY)
    private List<Driver> driverList = new ArrayList<>();

    @Override
    public String toString() {
        return "Bus{" +
                ", company='" + company + '\'' +
                ", busType=" + busType +
                '}';
    }
}
