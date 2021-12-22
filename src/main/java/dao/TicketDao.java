package dao;

import model.Ticket;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class TicketDao extends BaseDao{
    Session session;

    public void save(Ticket ticket) {
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.persist(ticket);
        session.getTransaction().commit();
        session.close();
    }
}
