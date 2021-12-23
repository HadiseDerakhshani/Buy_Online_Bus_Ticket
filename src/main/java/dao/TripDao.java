package dao;


import dto.TripDto;
import lombok.Data;
import model.Bus;
import model.Trip;
import model.enums.BusType;
import org.hibernate.Criteria;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.criterion.*;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class TripDao extends BaseDao {
    Session session;
    int selectPage = 0;
    int totalRecords;
    int numPage;
    int countCheck;
    Criteria criteriaFirst;

    public List<TripDto> searchTrip(String originCity, String destinationCity, Date moveDate, int numResult) {

        session = builderSession().openSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(Trip.class, "t");
        criteria.createAlias("t.buses", "b");
        SimpleExpression origin = Restrictions.eq("t.origin", originCity);
        SimpleExpression destination = Restrictions.eq("t.destination", destinationCity);
        SimpleExpression date = Restrictions.eq("t.moveDate", moveDate);
        Conjunction and = Restrictions.and(origin, destination, date);
        criteria.add(and);
        ///****************************

        criteria.setProjection(Projections.projectionList()
                .add(Projections.property("t.id").as("tripNumber"))
                .add(Projections.property("t.moveTime").as("moveTime"))
                .add(Projections.property("t.price").as("price"))
                .add(Projections.property("b.company").as("company"))
                .add(Projections.property("b.busType").as("busType"))
                .add(Projections.property("b.numOfEmpty").as("numOfEmpty"))
        );

        criteria.setResultTransformer(Transformers.aliasToBean(TripDto.class));
        List<TripDto> list1 = new ArrayList<>();
        criteriaFirst = criteria;
        List<TripDto> tripDtoList = pageResult(0, numResult);
        countCheck = 1;
        session.getTransaction().commit();
        session.close();

        return tripDtoList;


        //*******************************


    }

    public List<TripDto> pageResult(int firstResult, int numResult) {
        List<TripDto> list;
        boolean changePage = false;
        ScrollableResults scrollableResults = criteriaFirst.scroll();
        scrollableResults.last();
        if (countCheck == 1) {
            totalRecords = scrollableResults.getRowNumber() + 1;
            numPage = (totalRecords / numResult) + (totalRecords % numResult);
        } else
            countCheck++;
        scrollableResults.close();

        criteriaFirst.setFirstResult(firstResult);//id record of result first
        criteriaFirst.setMaxResults(numResult);//چندتا فیلد
        list = (List<TripDto>) criteriaFirst.list();

        return list;
    }

    public List<TripDto> filter(Criteria criteria, String company, BusType busType, int price1, int price2) {
        SimpleExpression condCompany = Restrictions.eq("b.company", company);
        SimpleExpression condBusType = Restrictions.eq("b.busType", busType);
        Criterion condPrice = Restrictions.between("t.price", price1, price2);
        Conjunction and = Restrictions.and(condCompany, condBusType, condPrice);

        criteria.add(and);
        criteria.addOrder(Order.asc("t.moveTime"));
        criteria.setProjection(Projections.projectionList()
                .add(Projections.property("t.id").as("tripNumber"))
                .add(Projections.property("t.moveTime").as("moveTime"))
                .add(Projections.property("t.price").as("price"))
                .add(Projections.property("b.company").as("company"))
                .add(Projections.property("b.busType").as("busType"))
                .add(Projections.property("b.numOfEmpty").as("numOfEmpty")));

        criteria.setResultTransformer(Transformers.aliasToBean(TripDto.class));
        session.getTransaction().commit();
        session.close();

        return criteria.list();
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
        query.setParameter("reserve", trip.getNumOfReserve()+1);
        query.setParameter("reserve", trip.getNumOfEmpty()-1);
        query.executeUpdate();
        session.getTransaction().commit();
        session.close();

    }
}
