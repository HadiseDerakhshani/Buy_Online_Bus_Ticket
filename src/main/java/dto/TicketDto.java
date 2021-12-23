package dto;

import lombok.Data;
import model.enums.BusType;

import java.util.Date;

@Data
public class TicketDto {
    //check
    private String company;
    private BusType busType;
    private int seatEmpty;
    private String origin;
    private String destination;
    private Date moveDate;
    private Date moveTime;
}
