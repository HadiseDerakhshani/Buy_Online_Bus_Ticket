package dao;

import model.Address;
import org.hibernate.Session;

import java.util.List;

public class AddressDao extends BusDao {
    private Session session;

    public void save(List<Address> addressList) {
        session = builderSession().openSession();
        session.beginTransaction();
        for (Address address : addressList)
            session.persist(address);
        session.getTransaction().commit();
        session.close();
    }

}
