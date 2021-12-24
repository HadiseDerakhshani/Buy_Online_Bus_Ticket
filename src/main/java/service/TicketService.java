package service;

import dao.TicketDao;
import dto.TicketDto;
import model.Ticket;

import java.util.List;

public class TicketService {
    TicketDao ticketDao = new TicketDao();

    public void save(Ticket ticket) {
        ticketDao.save(ticket);
    }

    public List<TicketDto> showReport() {
        return ticketDao.showReport();

    }
}
