package model;

import lombok.Data;

@Data
public class Manager extends Person {
    private String userName = "admin";
    private String password = "admin";

    public Manager() {
    }
}
