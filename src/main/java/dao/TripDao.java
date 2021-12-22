package dao;


import dto.TripDto;
import model.Trip;
import model.enums.BusType;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.*;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;

import java.util.Date;
import java.util.List;

public class TripDao extends BaseDao {
    Session session;
    //search city & date to input
    //extend basedao
    //dto create
    public Criteria searchTrip(String originCity, String destinationCity, Date moveDate){

    session = sessionFactory.openSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(Trip.class,"t");
        criteria.createAlias("t.buses","b");
        SimpleExpression origin = Restrictions.eq("t.origin", originCity);
        SimpleExpression destination = Restrictions.eq("t.destination", destinationCity);
        SimpleExpression date = Restrictions.eq("t.moveDate", moveDate);
        Conjunction and = Restrictions.and(origin, destination, date);
        criteria.add(and);
        session.getTransaction().commit();
        session.close();
        return criteria;
    }

   public List<TripDto> filter(Criteria  criteria, String company, BusType busType,int price1,int price2){
       SimpleExpression condCompany = Restrictions.eq("b.company", company);
       SimpleExpression condBusType =Restrictions.eq("b.busType", busType);
       Criterion condPrice = Restrictions.between("t.price", price1, price2);
       Conjunction and = Restrictions.and(condCompany,condBusType,condPrice);

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

   public Trip findById(int id){
        session=sessionFactory.openSession();
        session.beginTransaction();
       Query query = session.createQuery("from Trip where id=:id");
           query.setParameter("id",id);
       Trip trip =(Trip) query.uniqueResult();

      /* SimpleExpression eq = Restrictions.eq("t.id", id);
      criteria.add(eq);
      Trip trip =(Trip) criteria.uniqueResult();*/
      return trip;
   }
}
