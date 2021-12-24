package view;

import dto.TicketDto;
import dto.TripDto;
import exception.InValidUserInfoException;
import model.Passenger;
import model.Ticket;
import model.Trip;
import model.enums.Gender;
import service.PassengerService;
import service.TicketService;
import service.TripService;
import utils.ValidationUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserView {
    TripService tripService = new TripService();
    Scanner scanner = new Scanner(System.in);
    List<Ticket> ticketList = new ArrayList<>();
    TicketService ticketService = new TicketService();
    PassengerService passengerService = new PassengerService();
    boolean exit = false;

    public void searchTrip(String info, int firstResult, int numResult) throws ParseException {//scanner & Exception & sout
        List<TripDto> list = tripService.searchTrip(info, firstResult, numResult);
        int countPage = 1;
        boolean changePage = false;
        do {
            System.out.println("----------------------------------");
            list.forEach(System.out::println);
            System.out.println(" select next page or previous page of result :\n 1.next\n2.previous\n" +
                    "3.filter result\n 4.buy Ticket menu\n 5.exit");
            try {
                ValidationUtils.isValidSelectSearch(scanner.next());
                int selectPage = scanner.nextInt();
                switch (selectPage) {
                    case 1:
                        if (list.size() < numResult && list == null) {
                            System.out.println("page not exit you are in last page");
                            changePage = false;
                        } else {
                            firstResult = firstResult + numResult;
                            list = tripService.searchTrip(info, firstResult, numResult);
                        }
                        break;
                    case 2:
                        if (firstResult == 0) {
                            System.out.println("page not exit you are in first page");
                            changePage = false;
                        } else {
                            firstResult = (firstResult - numResult < 0) ? 0 : (firstResult - numResult);
                            list = tripService.searchTrip(info, firstResult, numResult);
                        }
                        break;
                    case 3:
                        filter(list);
                        break;
                    case 4:
                        System.out.println("number of trip for buy ticket : ");
                        ValidationUtils.isValidNumeric(scanner.next());
                        int numberTrip = Integer.parseInt(scanner.next());
                        Trip selectTrip = tripService.findById(numberTrip);
                        System.out.println("how many ticket : ");
                        int ticketCount = Integer.parseInt(scanner.next());//////
                        for (int i = 0; i < ticketCount; i++) {
                            List<Ticket> tickets = buyTicket(selectTrip);
                            tickets.forEach(System.out::println);
                            ticketList.addAll(tickets);
                        }
                        changePage = true;
                        break;
                    case 5:
                        changePage = true;
                        exit = true;
                        break;

                }//switch
            } catch (InValidUserInfoException e) {
                e.getMessage();
                changePage = false;
            }
        } while (changePage);


    }


    public void filter(List<TripDto> list) {
        List<TripDto> tripDtoList = new ArrayList<>();
        boolean isContinue = false;
        String[] split;
        do {
            System.out.println("Filter By 1.Company Bus\n 2.BusType\n 3.Price Range\n 4.Option 1 & 2\n " +
                    "5.Option 1 & 3\n 6.Option 2 & 3\n 7.All Option\n 8.Exit ");
            String chose = scanner.next();
            try {
                ValidationUtils.isValidSelectFilter(chose);
                switch (chose) {
                    case "1":
                        System.out.println("Enter Name Of Company Bus : ");
                        tripDtoList = tripService.filter(list, scanner.next(), null, 0, 0);
                        isContinue = true;
                        break;
                    case "2":
                        System.out.println(" Enter Type Of Bus : ");
                        tripDtoList = tripService.filter(list, null, scanner.next(), 0, 0);
                        isContinue = true;
                        break;
                    case "3":
                        System.out.println("Enter Price Range Like Sample Price1,price2 : ");
                        split = scanner.next().split(",");
                        tripDtoList = tripService.filter(list, null, null, Integer.parseInt(split[0]), Integer.parseInt(split[1]));
                        isContinue = true;
                        break;
                    case "4":
                        System.out.println("Enter Name Type Of Bus  Like Sample Company,bustype: ");
                        split = scanner.next().split(",");
                        tripDtoList = tripService.filter(list, split[0], split[1], 0, 0);
                        isContinue = true;
                        break;
                    case "5":
                        System.out.println("Enter CompanyName & Price Range Like Sample Company,price1,price2 : ");
                        split = scanner.next().split(",");
                        tripDtoList = tripService.filter(list, split[0], null, Integer.parseInt(split[1]), Integer.parseInt(split[2]));
                        isContinue = true;
                        break;
                    case "6":
                        System.out.println("Enter  Bustype & Price Range Like Sample BusType,price1,price2 : ");
                        split = scanner.next().split(",");
                        tripDtoList = tripService.filter(list, null, split[0], Integer.parseInt(split[1]), Integer.parseInt(split[2]));
                        isContinue = true;
                        break;
                    case "7":
                        System.out.println("Enter CompanyName BusType & Price Range Like Sample CompanyName,busType,price1,price2 : ");
                        split = scanner.next().split(",");
                        tripDtoList = tripService.filter(list, split[0], split[1], Integer.parseInt(split[2]), Integer.parseInt(split[3]));
                        isContinue = true;
                        break;
                }

            } catch (InValidUserInfoException e) {
                e.getMessage();
                isContinue = false;
            }
        } while (isContinue);

        tripDtoList.forEach(System.out::println);

    }

    public List<Ticket> buyTicket(Trip trip) {
        System.out.println("enter number ticket : ");
        for (int i = 0; i < scanner.nextInt(); i++) {
            Ticket ticket = new Ticket();
            Passenger passenger = createPassenger();
            ticket.setTrip(trip);
            int reserve = (trip.getNumOfReserve()) + 1;
            tripService.update(trip);
            ticket.setSeatCount(reserve);
            ticket.setPassenger(passenger);
            passenger.setTicket(ticket);
            ticketService.save(ticket);
            passengerService.save(passenger);
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
        if (split[4].toUpperCase().equals(Gender.FEMALE.name())) {
            passenger.setGender(Gender.FEMALE);
        } else passenger.setGender(Gender.MALE);
        passenger.setAge(Integer.parseInt(split[5]));
        return passenger;
    }

    public void showReport() {
        List<TicketDto> ticketDto = ticketService.showReport();
        System.out.println("number of ticket sold is ==> " + ticketList.size());
        ticketDto.forEach(System.out::println);

    }
}
