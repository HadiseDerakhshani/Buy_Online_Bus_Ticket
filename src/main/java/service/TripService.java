package service;

import dao.PassengerDao;
import dao.TicketDao;
import dao.TripDao;
import dto.TripDto;
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

public class TripService {
    Scanner scanner = new Scanner(System.in);
    int selectPage = 0;
    int numberTicket;
    static final TripDao tripDao = new TripDao();

    public void searchTrip(String info) throws ParseException {//scanner & Exception & sout

        String[] input = info.split(",");
        Criteria criteria = tripDao.searchTrip(input[0], input[1]
                , new SimpleDateFormat("yyyy-mm-dd").parse(input[2]));
        int numResult = Integer.parseInt(input[3]);
        int firstResult = 0;
        int countPage = 1;
        boolean changePage = false;

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
        Criteria criteria1 = criteria;
        //************************************************************
        do {
            ScrollableResults scrollableResults = criteria.scroll();
            scrollableResults.last();
            int totalRecords = scrollableResults.getRowNumber() + 1;
            int numPage = (totalRecords / numResult) + (totalRecords % numResult);
            scrollableResults.close();
            //
            //   criteria.setFirstResult(3+5+5+5+5+5+5+5);//id record of result first
            criteria.setFirstResult(firstResult);//id record of result first
            criteria.setMaxResults(numResult);//چندتا فیلد
            List<TripDto> list = criteria.list();
            if (list != null) {
                System.out.println("----------------------------------");
                System.out.println("Page Number ==> " + countPage + " of " + numPage);
                for (TripDto tripDto : list) {
                    System.out.println(numberTicket + " : " + tripDto);
                }

                if (numPage > 0) {
                    System.out.println(" select next page or previous page of result :\n 1.next\n2.previous");
                    //invalid input
                    selectPage = scanner.nextInt();
                    if (selectPage == 2) {
                        if (countPage == 1) {
                            changePage = checkPage("first");
                        } else
                            firstResult = (firstResult - numResult < 0) ? 0 : (firstResult - numResult);
                    } else if (selectPage == 1) {
                        if (countPage == numPage) {
                            changePage = checkPage("last");
                        } else
                            firstResult = firstResult + numResult;
                    }
                }
            }
        } while (changePage);
        if (selectPage == 2) {
            System.out.println("filter by 1.company bus 2.busType 3.price range 4.1 & 2  5.1&3 6.2&3 7.all 8.exit ");
            switch (scanner.nextInt()) {

                case 1:
                    System.out.println("enter name of company bus : ");
                    list1 = tripDao.filter(criteria, scanner.next(), null, 0, 0);
                    break;
                case 2:
                    System.out.println("enter type of bus : ");
                    list1 = tripDao.filter(criteria, null, BusType.valueOf(scanner.next().toUpperCase()), 0, 0);
                    break;
                case 3:
                    System.out.println("enter price range like sample price1,price2 : ");
                    String[] split = scanner.next().split(",");
                    list1 = tripDao.filter(criteria, null, null, Integer.parseInt(split[0]), Integer.parseInt(split[1]));
                    break;
                case 4:
                    System.out.println("enter name type of bus  like sample company,busType: ");
                    String[] split1 = scanner.next().split(",");
                    list1 = tripDao.filter(criteria, split1[0], BusType.valueOf(split1[1].toUpperCase()), 0, 0);
                    break;
                case 5:
                    System.out.println("enter companyName & price range like sample company,price1,price2 : ");
                    String[] split2 = scanner.next().split(",");
                    list1 = tripDao.filter(criteria, split2[0], null, Integer.parseInt(split2[1]), Integer.parseInt(split2[2]));
                    break;
                case 6:
                    System.out.println("enter  busType & price range like sample busType,price1,price2 : ");
                    String[] split3 = scanner.next().split(",");
                    list1 = tripDao.filter(criteria, null, BusType.valueOf(split3[0].toUpperCase()), Integer.parseInt(split3[1]), Integer.parseInt(split3[2]));
                    break;
                case 7:
                    System.out.println("enter companyName busType & price range like sample companyName,busType,price1,price2 : ");
                    String[] split4 = scanner.next().split(",");
                    list1 = tripDao.filter(criteria, split4[0], BusType.valueOf(split4[1].toUpperCase()), Integer.parseInt(split4[2]), Integer.parseInt(split4[3]));
                    break;
                case 8:
                    selectPage = 4;
                    break;

            }
            ////
            // pageResult(list1,numResult);
        } else if (selectPage == 3) {
            System.out.println("how many trip you want : ");
            System.out.println("number of trip for buy ticket : ");
            Trip selectTrip = tripDao.findById(criteria, scanner.nextInt());
            int tripCount = scanner.nextInt();
           for (int i=0;i<tripCount;i++){
               List<Passenger> passengerList = buyTicket(selectTrip);
               System.out.println(passengerList);
           }
        } else if (selectPage == 4) {
            //TODO
        }

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

    /* public void pageResult(List<TripDto> list,int numResult){
         int firstResult = 0;
         int countPage = 1;
         boolean changePage = false;
         int selectPage=0;
     }*/
    public List<Passenger> buyTicket(Trip trip) {
        TicketDao ticketDao = new TicketDao();
        PassengerDao passengerDao = new PassengerDao();
        System.out.println("enter number ticket : ");
        List<Passenger> passengerList = new ArrayList<>();
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
            passengerList.add(passenger);
        }
        return passengerList;

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
}
