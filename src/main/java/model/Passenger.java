package model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;


@Data
@ToString(callSuper = true)
@Entity
public class Passenger extends Person {
    @OneToOne(cascade = CascadeType.ALL)
    private Ticket ticket;
  //  @OneToMany(cascade = CascadeType.ALL )  //ok
   // private List<Ticket> ticketList;
    public Passenger() {
    }
}
