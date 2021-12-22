package view;

import model.Manager;
import service.TripService;

import java.text.ParseException;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean isContinue = false;
        printHeaderPart("WELL COME TO ONLINE BUS TICKET PURCHASE SYSTEM");
        do {
            System.out.println("Select Item :\n 1.Manager\n 2.Passenger");
            //invalid select
            int select = Integer.parseInt(scanner.next());
            switch (select) {
                case 1:
                    managerMenu();
                    break;
                case 2:
                    passengerMenu();
                    break;
                default:
                    isContinue = tryAgain();
                    break;
            }
        } while (isContinue);
    }

    public static void managerMenu() {
        printHeaderPart("Manager Menu");
        boolean isContinue = false;
        Manager manager = new Manager();
        do {
            System.out.println("Enter UserName : ");
            //is valid input
            String username = scanner.next();
            System.out.println("Enter Password : ");
            //is valid input
            String password = scanner.next();
            if (username.toLowerCase().equals(manager.getUserName()) && password.toLowerCase().equals(manager.getPassword()))
                isContinue = true;
            isContinue = tryAgain();
        } while (true);
    }

    public static void passengerMenu() {
        TripService tripService = new TripService();
        printHeaderPart("Passenger Menu");
        System.out.println("Enter Information Like Sample: Origin City,Destination City,Date :");
        String info = scanner.next();
        System.out.println("enter Number Of Result : ");
        int s = scanner.nextInt();
        try {
            tripService.searchTrip(info, 0, s);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        // String originCity= input[0];


    }

    public static boolean tryAgain() {
        System.out.println("-----Entered Is Not Valid ------\n Please Try Again");
        return false;
    }

    public static void printHeaderPart(String partName) {
        System.out.println("**********" + partName + "**********");
    }
}
