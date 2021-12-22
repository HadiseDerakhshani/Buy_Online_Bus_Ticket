package dao;

import model.Passenger;
import org.hibernate.Session;

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
