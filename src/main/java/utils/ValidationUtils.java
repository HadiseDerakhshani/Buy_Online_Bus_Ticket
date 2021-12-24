package utils;

import exception.InValidUserInfoException;

public class ValidationUtils {
    public static boolean isValidMenu(String input) {
        if (input.matches("[1-5]+"))
            return true;
        throw new InValidUserInfoException("----entered is not valid you should enter number 1 or 2----");
    }

    public static boolean isValidUsername(String input) {
        if (input.matches("^[a-zA-Z]+$"))
            return true;
        throw new InValidUserInfoException("----entered is not valid you should enter alphabet----");
    }

    public static boolean isValidInfo(String input) {
        String check = "";
        String[] split = input.split(",");
        if (split[0].matches("^[a-zA-Z]+$")) {
            check = "origin city";
            return true;
        } else if (split[1].matches("^[a-zA-Z]+$")) {
            check = "destination city";
            return true;
        } else if (split[2].matches("^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$")) {
            check = "date";
            return true;
        }
        throw new InValidUserInfoException("---- " + check + " is not valid you should enter alphabet----");
    }

    public static boolean isValidNumeric(String input) {
        if (input.matches("[0-9]+"))
            return true;
        throw new InValidUserInfoException("----entered is not valid you should enter numeric----");
    }

    public static boolean isValidSelectSearch(String input) {
        if (input.matches("[1-5]+"))
            return true;
        throw new InValidUserInfoException("----entered is not valid you should enter number 1-5 ----");
    }

    public static boolean isValidSelectFilter(String input) {
        if (input.matches("[1-8]+"))
            return true;
        throw new InValidUserInfoException("----entered is not valid you should enter number 1-8 ----");
    }
   /* public  static boolean isValidSplitFilter(String input){
        if(input.matches("[1-8]+"))
            return  true;
        throw  new InValidUserInfoException("----entered is not valid you should enter number 1-8 ----");
    }*/
}
