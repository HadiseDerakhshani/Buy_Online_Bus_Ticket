package dao;

import lombok.Data;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@Data
public class BaseDao {
    public static SessionFactory sessionFactory;
    public static SessionFactory builderSession() {
        if (sessionFactory == null) {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        }
        return sessionFactory;
    }
}
