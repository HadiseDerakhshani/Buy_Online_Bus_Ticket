package model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;


@Data
@ToString(callSuper = true)
@Entity
public class Passenger extends Person {
    @OneToOne(cascade = CascadeType.ALL)
    private Ticket ticket;

    public Passenger() {
    }
}
