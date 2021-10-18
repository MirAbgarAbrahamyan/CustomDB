package helper;

public class Utility {
    public static final char SEPARATOR = 0x1c;
    public static final String EXTENSION = ".txt";

    public static void printWelcome() {
        System.out.println("HELLO AND WELCOME TO THE WORST DATABASE EVER IN THIS WORLD!");
        System.out.println("OPTIONS:");
        System.out.println("add data,data,data,...\tis for adding a new row in the table");
        System.out.println("view\tis for showing data in the table");
        System.out.println("search *\t is equivalent to view");
        System.out.println("search * where x is y\t shows all rows where column x = y");
        System.out.println("search z where x is y\t shows all data in column z from each row where column x = y");
        System.out.println("switch\tafter entering switch you will be asked to enter the preferred table's name");
        System.out.println("exit\tis for quiting the WORST-DATABASE-EVER shell (wdbesh)\n\n\n");
    }

    private static boolean isInteger(String str) {
        if (str.isEmpty()) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            if (i == 0 && str.charAt(i) == '-') {
                if (str.length() == 1) {
                    return false;
                } else {
                    continue;
                }
            }
            if (Character.digit(str.charAt(i), 10) < 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean checkForRightInputData(String[] input, String[] dataTypes) {
        for (int i = 0; i < input.length; i++) {
            if (dataTypes[i].equalsIgnoreCase("integer") && !isInteger(input[i])) {
                return false;
            } else if (dataTypes[i].equalsIgnoreCase("character") && input[i].length() != 1) {
                return false;
            }
        }
        return true;
    }
}
