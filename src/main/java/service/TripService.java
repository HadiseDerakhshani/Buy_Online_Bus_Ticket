package service;

import dao.PassengerDao;
import dao.TicketDao;
import dao.TripDao;
import dto.TripDto;
import lombok.Data;
import model.Passenger;
import model.Ticket;
import model.Trip;
import model.enums.BusType;
import model.enums.Gender;
import org.hibernate.Criteria;
import org.hibernate.ScrollableResults;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Data
public class TripService {
    static final TripDao tripDao = new TripDao();
    Scanner scanner = new Scanner(System.in);
    int selectPage = 0;
    int numberTicket;
    int totalRecords;
    int numPage;
    int countCheck;
    Criteria criteriaFirst;

    public List<TripDto> searchTrip(String info, int firstResult, int numResult) throws ParseException {//scanner & Exception & sout

        String[] input = info.split(",");
        Criteria criteria = tripDao.searchTrip(input[0], input[1]
                , new SimpleDateFormat("yyyy-mm-dd").parse(input[2]));

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
        pageResult(0, numResult);
        countCheck = 1;
        Criteria criteria1 = criteria;
        //************************************************************
        // pageResult(list1,numResult);
        if (selectPage == 3) {
            System.out.println("how many trip you want : ");
            System.out.println("number of trip for buy ticket : ");
            Trip selectTrip = tripDao.findById(scanner.nextInt());
            int tripCount = scanner.nextInt();
            for (int i = 0; i < tripCount; i++) {
                List<Ticket> ticketList = buyTicket(selectTrip);
                System.out.println(ticketList);
            }
        } else if (selectPage == 4) {
            //TODO
        }
        return criteria.list();
    }

    public boolean checkPage(String page) {
        System.out.println("page not exit you are in " + page + " page");
        System.out.println("select item :\n1.continue\n 2.filter result\n 3.buy Ticket menu\n 4.exit");
        int select = scanner.nextInt();
        if (select == 1)
            return false;
        else
            return true;

    }

    public List<TripDto> pageResult(int firstResult, int numResult) {

        List<TripDto> list = new ArrayList<>();
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

    public Trip findById(int id) {
        return tripDao.findById(id);
    }

    public List<Ticket> buyTicket(Trip trip) {
        TicketDao ticketDao = new TicketDao();
        PassengerDao passengerDao = new PassengerDao();
        System.out.println("enter number ticket : ");
        List<Ticket> ticketList = new ArrayList<>();
        for (int i = 0; i < scanner.nextInt(); i++) {

            Ticket ticket = new Ticket();
            Passenger passenger = createPassenger();
            ticket.setTrip(trip);
            ticket.setSeatCount((trip.getBuses().getNumOfReserve()) + 1);
            // trip.getBuses().setNumOfReserve();
            //sandali
            ticket.setPassenger(passenger);
            passenger.setTicket(ticket);
            ticketDao.save(ticket);
            passengerDao.save(passenger);
            ticketList.add(ticket);
        }
        return ticketList;

    }

    public Passenger createPassenger() {
        Passenger passenger = new Passenger();
        System.out.println("enter info like sample name,name,family,nationalCode,phoneNumber,gender,age :");
        String[] split = scanner.next().split(",");
        passenger.setName(split[0]);
        passenger.setFamily(split[1]);
        passenger.setNationalCode(split[2]);
        passenger.setPhoneNumber(split[3]);
        if (split[4].toUpperCase().equals(Gender.FEMALE)) {
            passenger.setGender(Gender.FEMALE);
        } else passenger.setGender(Gender.MALE);
        passenger.setAge(Integer.parseInt(split[5]));
        return passenger;
    }

    public List<TripDto> filter(String company, BusType busType, int price1, int price2) {
        List<TripDto> filter = tripDao.filter(criteriaFirst, scanner.next(), null, 0, 0);
        return filter;
    }
}
