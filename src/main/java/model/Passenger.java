package model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;
@Builder
@NoArgsConstructor
@Data
@ToString(callSuper = true)
@Entity
public class Passenger extends Person {
    @OneToOne(cascade = CascadeType.ALL)
    private Ticket ticket ;


}
