package dao;

import dto.TicketDto;
import model.Ticket;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;

import java.util.List;

public class TicketDao extends BaseDao {
    Session session;

    public void save(Ticket ticket) {
        session = builderSession().openSession();
        session.beginTransaction();
        session.persist(ticket);
        session.getTransaction().commit();
        session.close();
    }

    public List<TicketDto> showReport() {

        session = builderSession().openSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(Ticket.class, "tic");
        criteria.createAlias("tic.trip", "trip");
        criteria.createAlias("trip.buses", "bus");
        criteria.addOrder(Order.desc("trip.moveDate"));
        criteria.addOrder(Order.desc("trip.moveTime"));
        criteria.setProjection(Projections.projectionList()
                .add(Projections.property("bus.company").as("company"))//
                .add(Projections.property("bus.busType").as("busType"))//
                .add(Projections.property("trip.numOfEmpty").as("seatEmpty"))//
                .add(Projections.property("trip.origin").as("origin"))
                .add(Projections.property("trip.destination").as("destination"))
                .add(Projections.property("trip.moveDate").as("moveDate"))
                .add(Projections.property("trip.moveTime").as("moveTime"))
        );

        criteria.setResultTransformer(Transformers.aliasToBean(TicketDto.class));
        List<TicketDto> list = (List<TicketDto>) criteria.list();
        return list;
    }
}
