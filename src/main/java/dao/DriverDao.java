package dao;

import model.Driver;
import org.hibernate.Session;

import java.util.List;

public class DriverDao extends BaseDao {
    private Session session;

    public void save(List<Driver> driverList) {
        session = builderSession().openSession();
        session.beginTransaction();
        for (Driver driver : driverList)
            session.persist(driver);
        session.getTransaction().commit();
        session.close();
    }
}
