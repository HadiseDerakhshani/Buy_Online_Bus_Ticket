package dao;

import model.Ticket;
import org.hibernate.Session;

public class TicketDao extends BaseDao {
    Session session;

    public void save(Ticket ticket) {
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.persist(ticket);
        session.getTransaction().commit();
        session.close();
    }
}
