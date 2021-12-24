package dao;


import dto.TripDto;
import lombok.Data;
import model.Trip;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;

import java.util.Date;
import java.util.List;

@Data
public class TripDao extends BaseDao {
    private Session session;


    public void save(List<Trip> tripList) {
        session = builderSession().openSession();
        session.beginTransaction();
        for (Trip trip : tripList)
            session.persist(trip);
        session.getTransaction().commit();
        session.close();
    }

    public List<TripDto> searchTrip(String originCity, String destinationCity, Date moveDate, int firstResult, int numResult) {

        session = builderSession().openSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(Trip.class, "t");
        criteria.createAlias("t.buses", "b");
        SimpleExpression origin = Restrictions.eq("t.origin", originCity);
        SimpleExpression destination = Restrictions.eq("t.destination", destinationCity);
        SimpleExpression date = Restrictions.eq("t.moveDate", moveDate);
        Conjunction and = Restrictions.and(origin, destination, date);
        criteria.add(and);

        criteria.setFirstResult(firstResult);//id record of result first
        criteria.setMaxResults(numResult);
        criteria.setProjection(Projections.projectionList()
                .add(Projections.property("t.id").as("tripNumber"))
                .add(Projections.property("t.moveTime").as("moveTime"))
                .add(Projections.property("t.price").as("price"))
                .add(Projections.property("b.company").as("company"))
                .add(Projections.property("b.busType").as("busType"))
                .add(Projections.property("b.numOfEmpty").as("numOfEmpty"))
        );
        criteria.setResultTransformer(Transformers.aliasToBean(TripDto.class));
        List<TripDto> list1 = criteria.list();

        session.getTransaction().commit();
        session.close();

        return list1;

    }


    public Trip findById(int id) {
        session = builderSession().openSession();
        session.beginTransaction();
        Query query = session.createQuery("from Trip where id=:id");
        query.setParameter("id", id);
        Trip trip = (Trip) query.uniqueResult();

        return trip;
    }

    public void update(Trip trip) {
        session = builderSession().openSession();
        session.beginTransaction();
        Query query = session.createQuery(" update Trip set numOfReserve=:reserve ,numOfEmpty=:empty where id=:id");
        query.setParameter("id", trip.getId());
        query.setParameter("reserve", trip.getNumOfReserve() + 1);
        query.setParameter("reserve", trip.getNumOfEmpty() - 1);
        query.executeUpdate();
        session.getTransaction().commit();
        session.close();

    }
}
