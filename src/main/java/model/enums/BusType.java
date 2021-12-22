package model.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum BusType {
    VIP(30),
    NORMAL(45);
    private int seatCount;


}
