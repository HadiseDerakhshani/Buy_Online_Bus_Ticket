package model;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.util.Date;

@Data
@Entity
public class Driver extends Person {
    private Date birthDate;
    private Date BeginContract;
    @OneToOne
    private Address address;
    public Driver() {
    }
}
