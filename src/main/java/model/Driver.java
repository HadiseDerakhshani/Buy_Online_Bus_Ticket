package model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.util.Date;

@Data
@Entity
public class Driver extends Person {
    private Date birthDate;
    private Date beginning;
    @OneToOne
    private Address address;
    @ManyToOne
    private Bus bus;
}
