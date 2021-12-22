package dto;

import lombok.Data;

import java.util.Date;
@Data
public class TripDto {
    private Date moveTime;
    private String company;
    private String busType;
    private double price;
    private int numOfEmpty;
    int tripNumber;
}
