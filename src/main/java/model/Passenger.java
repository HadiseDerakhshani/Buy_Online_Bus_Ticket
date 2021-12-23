package model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;


@NoArgsConstructor
@Data
@ToString(callSuper = true)
@Entity
public class Passenger extends Person {
    @OneToOne(cascade = CascadeType.ALL)
    private Ticket ticket;


}
