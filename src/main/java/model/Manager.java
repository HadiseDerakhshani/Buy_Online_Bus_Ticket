package model;

import lombok.Data;

import javax.persistence.Entity;

@Data
@Entity
public class Manager extends Person {
    private String userName = "admin";
    private String password = "admin";
}
