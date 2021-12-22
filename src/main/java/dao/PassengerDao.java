package dao;

import dto.PassengerDto;
import dto.TripDto;
import model.Passenger;
import model.Ticket;
import model.Trip;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.hibernate.transform.Transformers;

import java.util.ArrayList;
import java.util.List;

public class PassengerDao extends BaseDao {
    Session session;

    public void save(Passenger passenger) {
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.persist(passenger);
        session.getTransaction().commit();
        session.close();
    }
  /*  public List<PassengerDto> createPassengerDto(List<Passenger> passengerList){
        List<PassengerDto> pDto=new ArrayList<>();
                session = sessionFactory.openSession();
        session.beginTransaction();

                Criteria criteria = session.createCriteria(Passenger.class,"p");
                for(Passenger passenger:passengerList) {
                    criteria.add(Restrictions.eq("p.id", passenger.getId()));
                    criteria.setProjection(Projections.projectionList()
                            .add(Projections.property("p.name").as("name"))
                            .add(Projections.property("p.family").as("family"))
                            .add(Projections.property("t.price").as("price"))
                            .add(Projections.property("b.company").as("company"))
                            .add(Projections.property("b.busType").as("busType"))
                            .add(Projections.property("b.numOfEmpty").as("numOfEmpty"))
                    );
                    criteria.setResultTransformer(Transformers.aliasToBean(TripDto.class));
                }
        session.getTransaction().commit();
        session.close();

        return null;
    }*/
}
