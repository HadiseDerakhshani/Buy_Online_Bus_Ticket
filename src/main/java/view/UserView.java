package view;

import dto.TicketDto;
import dto.TripDto;
import exception.InValidUserInfoException;
import model.Passenger;
import model.Ticket;
import model.Trip;
import model.enums.BusType;
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
    BusService busService = new BusService();
    int selectPage = 0;
    int numberTicket;
    //

    public void searchTrip(String info, int numResult) throws ParseException {//scanner & Exception & sout
        List<TripDto> list = tripService.searchTrip(info, numResult);
        int countPage = 1;
        boolean changePage = false;
        int numPage = tripService.getNumPage(), firstResult = 0;
        do {
            if (list != null) {
                System.out.println("----------------------------------");
                System.out.println("Page Number ==> " + countPage + " of " + numPage);
                for (TripDto tripDto : list) {
                    System.out.println(numberTicket + " : " + tripDto);
                }

                if (numPage > 1) {
                    System.out.println(" select next page or previous page of result :\n 1.next\n2.previous\n" +
                            "3.filter result\n 4.buy Ticket menu\n 5.exit");
                    try {
                        ValidationUtils.isValidSelectSearch(scanner.next());
                        selectPage = scanner.nextInt();
                        switch (selectPage) {
                            case 1:
                                if (countPage == numPage) {
                                    System.out.println("page not exit you are in last page");
                                    changePage = false;
                                } else {
                                    firstResult = firstResult + numResult;
                                    list = tripService.pageResult(firstResult, numResult);
                                    countPage += 1;
                                }
                                break;
                            case 2:
                                if (countPage == 1) {
                                    System.out.println("page not exit you are in first page");
                                    changePage = false;
                                } else {
                                    firstResult = (firstResult - numResult < 0) ? 0 : (firstResult - numResult);
                                    list = tripService.pageResult(firstResult, numResult);
                                    countPage -= 1;
                                }
                                break;
                            case 3:
                                list = filter();
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
                            //5555
                        }//switch
                    } catch (InValidUserInfoException e) {
                        e.getMessage();
                        changePage = false;
                    }
                } else {
                    System.out.println(" select next page or previous page of result :\n 1.filter result\n 2.buy Ticket menu\n 3.exit");
                    try {
                        ValidationUtils.isValidSelectSearch(scanner.next());
                        selectPage = scanner.nextInt();
                        switch (selectPage) {
                            case 1:
                                filter();
                                break;
                            case 2:
                                System.out.println("number of trip for buy ticket : ");
                                ValidationUtils.isValidNumeric(scanner.next());
                                int numberBuy = Integer.parseInt(scanner.next());
                                Trip selectTrip = tripService.findById(numberBuy);
                                List<Ticket> ticketList = buyTicket(selectTrip);
                                ticketList.forEach(System.out::println);

                                changePage = true;
                                break;
                        }
                    } catch (InValidUserInfoException e) {
                        e.getMessage();
                        changePage = false;
                    }
                }

            }
        } while (changePage);


    }


    public List<TripDto> filter() {
        List<TripDto> tripDtoList = new ArrayList<>();
        boolean isContinue = false;
        String[] split;
        do {
            System.out.println("filter by 1.company\n bus\n 2.busType\n 3.price range\n 4.Option 1 & 2\n " +
                    "5.Option 1 & 3\n 6.Option 2 & 3\n 7.all option\n 8.exit ");
            try {
                ValidationUtils.isValidSelectFilter(scanner.next());
                switch (scanner.nextInt()) {

                    case 1:
                        System.out.println("enter name of company bus : ");
                        tripDtoList = tripService.filter(scanner.next(), null, 0, 0);
                        isContinue = true;
                        break;
                    case 2:
                        System.out.println("enter type of bus : ");
                        tripDtoList = tripService.filter(null, BusType.valueOf(scanner.next().toUpperCase()), 0, 0);
                        isContinue = true;
                        break;
                    case 3:
                        System.out.println("enter price range like sample price1,price2 : ");
                        split = scanner.next().split(",");
                        tripDtoList = tripService.filter(null, null, Integer.parseInt(split[0]), Integer.parseInt(split[1]));
                        isContinue = true;
                        break;
                    case 4:
                        System.out.println("enter name type of bus  like sample company,busType: ");
                        split = scanner.next().split(",");
                        tripDtoList = tripService.filter(split[0], BusType.valueOf(split[1].toUpperCase()), 0, 0);
                        isContinue = true;
                        break;
                    case 5:
                        System.out.println("enter companyName & price range like sample company,price1,price2 : ");
                        split = scanner.next().split(",");
                        tripDtoList = tripService.filter(split[0], null, Integer.parseInt(split[1]), Integer.parseInt(split[2]));
                        isContinue = true;
                        break;
                    case 6:
                        System.out.println("enter  busType & price range like sample busType,price1,price2 : ");
                        split = scanner.next().split(",");
                        tripDtoList = tripService.filter(null, BusType.valueOf(split[0].toUpperCase()), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
                        isContinue = true;
                        break;
                    case 7:
                        System.out.println("enter companyName busType & price range like sample companyName,busType,price1,price2 : ");
                        split = scanner.next().split(",");
                        tripDtoList = tripService.filter(split[0], BusType.valueOf(split[1].toUpperCase()), Integer.parseInt(split[2]), Integer.parseInt(split[3]));
                        isContinue = true;
                        break;
                }

            } catch (InValidUserInfoException e) {
                e.getMessage();
                isContinue = false;
            }
        } while (isContinue);

        return tripDtoList;
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
