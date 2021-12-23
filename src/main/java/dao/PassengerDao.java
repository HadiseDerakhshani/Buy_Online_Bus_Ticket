package dao;

import model.Passenger;
import org.hibernate.Session;

public class PassengerDao extends BaseDao {
    Session session;

    public void save(Passenger passenger) {
        session = builderSession().openSession();
        session.beginTransaction();
        session.persist(passenger);
        session.getTransaction().commit();
        session.close();
    }

}
