package view;

import dao.PassengerDao;
import dao.TicketDao;
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
import service.TripService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserView {
    TripService tripService=new TripService();
    Scanner scanner = new Scanner(System.in);
    int selectPage = 0;
    int numberTicket;
    public void searchTrip(String info,int firstResult,int numResult ) throws ParseException {//scanner & Exception & sout


        List<TripDto> list = tripService.searchTrip(info,firstResult,numResult);
        int countPage = 1;
        boolean changePage = false;
        int numPage = tripService.getNumPage();
        do {
            if (list != null) {
                System.out.println("----------------------------------");
                System.out.println("Page Number ==> " + countPage + " of " + numPage);
                for (TripDto tripDto : list) {
                    System.out.println(numberTicket + " : " + tripDto);
                }

                if (numPage > 1) {
                    System.out.println(" select next page or previous page of result :\n 1.next\n2.previous");
                    //invalid input
                    selectPage = scanner.nextInt();
                    if (selectPage == 2) {
                        if (countPage == 1) {
                            changePage = checkPage("first");
                        } else {
                            firstResult = (firstResult - numResult < 0) ? 0 : (firstResult - numResult);
                            list=tripService.pageResult(firstResult, numResult);
                            countPage-=1;
                        }
                    } else if (selectPage == 1) {
                        if (countPage == numPage) {
                            changePage = checkPage("last");
                        } else {
                            firstResult = firstResult + numResult;
                           list= tripService.pageResult(firstResult, numResult);
                           countPage+=1;
                        }
                    }
                }
            }
        }while (changePage);

        //region Description
        switch (selectPage){
            case 2:filter();
            break;
            case 3:
                System.out.println("how many trip you want : ");
                System.out.println("number of trip for buy ticket : ");
                Trip selectTrip = tripService.findById(scanner.nextInt());
                int tripCount = scanner.nextInt();
                for (int i=0;i<tripCount;i++){
                    List<Ticket> ticketList = buyTicket(selectTrip);
                    System.out.println(ticketList);
                }
        }
        if (selectPage == 4) {
            //TODO
        }
        //endregion
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
public void filter(){
        List<TripDto> tripDtoList=new ArrayList<>();
    System.out.println("filter by 1.company bus 2.busType 3.price range 4.1 & 2  5.1&3 6.2&3 7.all 8.exit ");
    switch (scanner.nextInt()) {

        case 1:
            System.out.println("enter name of company bus : ");
            tripDtoList = tripService.filter(scanner.next(), null, 0, 0);
            break;
        case 2:
            System.out.println("enter type of bus : ");
            tripDtoList =  tripService.filter( null, BusType.valueOf(scanner.next().toUpperCase()), 0, 0);
            break;
        case 3:
            System.out.println("enter price range like sample price1,price2 : ");
            String[] split = scanner.next().split(",");
            tripDtoList =  tripService.filter( null, null, Integer.parseInt(split[0]), Integer.parseInt(split[1]));
            break;
        case 4:
            System.out.println("enter name type of bus  like sample company,busType: ");
            String[] split1 = scanner.next().split(",");
            tripDtoList =  tripService.filter( split1[0], BusType.valueOf(split1[1].toUpperCase()), 0, 0);
            break;
        case 5:
            System.out.println("enter companyName & price range like sample company,price1,price2 : ");
            String[] split2 = scanner.next().split(",");
            tripDtoList =  tripService.filter( split2[0], null, Integer.parseInt(split2[1]), Integer.parseInt(split2[2]));
            break;
        case 6:
            System.out.println("enter  busType & price range like sample busType,price1,price2 : ");
            String[] split3 = scanner.next().split(",");
            tripDtoList = tripService.filter(null, BusType.valueOf(split3[0].toUpperCase()), Integer.parseInt(split3[1]), Integer.parseInt(split3[2]));
            break;
        case 7:
            System.out.println("enter companyName busType & price range like sample companyName,busType,price1,price2 : ");
            String[] split4 = scanner.next().split(",");
            tripDtoList = tripService.filter( split4[0], BusType.valueOf(split4[1].toUpperCase()), Integer.parseInt(split4[2]), Integer.parseInt(split4[3]));
            break;

    }
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
}
