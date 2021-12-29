package view;

import exception.InValidUserInfoException;
import model.Manager;
import service.DataBaseInitializer;
import utils.ValidationUtils;

import java.text.ParseException;
import java.util.Scanner;

public class Main {
    static final Scanner scanner = new Scanner(System.in);
    static final UserView userView = new UserView();
    static boolean isContinue;

    public static void main(String[] args) {
        try {
            DataBaseInitializer initializationEntity = new DataBaseInitializer();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        isContinue = false;
        printHeaderPart("WELL COME TO ONLINE BUS TICKET PURCHASE SYSTEM");
        do {
            System.out.println("Select Item :\n 1.Manager\n 2.Passenger");
            try {
                String select = scanner.next();
                ValidationUtils.isValidMenu(select);
                switch (select) {
                    case "1":
                        managerMenu();
                        isContinue = true;
                        break;
                    case "2":
                        passengerMenu();
                        isContinue = true;
                        break;

                }
            } catch (InValidUserInfoException e) {
                System.out.println(e.getMessage());
                isContinue = false;
            }
        } while (isContinue);
    }

    public static void managerMenu() {
        printHeaderPart("Manager Menu");
        isContinue = false;
        Manager manager = new Manager();
        do {
            try {
                System.out.println("Enter UserName : ");//is valid input
                String username = scanner.next();
                ValidationUtils.isValidUsername(username);
                System.out.println("Enter Password : ");
                String password = scanner.next();
                ValidationUtils.isValidUsername(password);
                if (username.toLowerCase().equals(manager.getUserName()) &&
                        password.toLowerCase().equals(manager.getPassword())) {
                    isContinue = true;
                    break;
                }
            } catch (InValidUserInfoException e) {
                System.out.println(e.getMessage());
                isContinue = false;
            }
        } while (isContinue);
        userView.showReport();

    }

    public static void passengerMenu() {
        isContinue = false;
        printHeaderPart("Passenger Menu");

        do {
            System.out.println("Enter Information Like Sample: Origin City,Destination City,yyyy/mm/dd:");
            String info = scanner.next();
            try {
                ValidationUtils.isValidInfo(info);
                System.out.println("enter Number Of Result : ");
                String numOfResult = scanner.next();
                ValidationUtils.isValidNumeric(numOfResult);
                userView.searchTrip(info, 0, Integer.parseInt(numOfResult));
                isContinue = true;
            } catch (InValidUserInfoException | ParseException e) {
                e.getMessage();
                isContinue = false;
            }
        } while (isContinue);

    }

    public static void printHeaderPart(String partName) {
        System.out.println("********** " + partName + " **********");
    }
}
